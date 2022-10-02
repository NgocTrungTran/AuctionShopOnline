package com.aso.controller.api;

import com.aso.exception.AttributesExistsException;
import com.aso.exception.DataInputException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Account;
import com.aso.model.JwtResponse;
import com.aso.model.Role;
import com.aso.model.dto.AccountDTO;
import com.aso.service.account.AccountService;
import com.aso.service.jwt.JwtService;
import com.aso.service.role.RoleService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AppUtil appUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<AccountDTO> optUser = accountService.findUserDTOByUsername(accountDTO.getUsername());

        if (optUser.isPresent()) {
            throw new AttributesExistsException ("Username already exists");
        }

        try {
            Account account = accountDTO.toAccount ();
            Optional<Role> role = roleService.findById ( 2L );
            account.setRole ( role.get () );
            accountService.save(account);

            return new ResponseEntity<>( HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken (account.getUsername(), account.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Account currentUser = accountService.getByUsername(account.getUsername()).get ();

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 60 * 1000)
                    .domain("localhost")
                    .build();

            System.out.println(jwtResponse);

            return ResponseEntity
                    .ok()
                    .header( HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);
        } catch (Exception e) {
            e.printStackTrace ();
            throw new ResourceNotFoundException ( "Login information is incorrect" );
        }
    }
}
