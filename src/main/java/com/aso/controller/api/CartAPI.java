package com.aso.controller.api;


import com.aso.model.Cart;
import com.aso.model.dto.CartDTO;
import com.aso.service.cart.CartService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/carts")
public class CartAPI {
    @Autowired
    private CartService cartService;

    @Autowired
    private AppUtil appUtils;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartByUserName(@PathVariable String id){
        return new ResponseEntity<>(cartService.findCartItemDTOByIdAccountInfo(id).get().toCart(),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody CartDTO cartDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            Cart createCart = cartService.save(cartDTO.toCart());
            return new ResponseEntity<>(createCart.toCartDTO(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("không thể tạo được đơn hàng",HttpStatus.BAD_REQUEST);
        }
    }
}
