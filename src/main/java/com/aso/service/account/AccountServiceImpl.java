package com.aso.service.account;

import com.aso.model.Account;
import com.aso.model.AccountPrinciple;
import com.aso.model.dto.AccountDTO;
import com.aso.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll ();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById ( id );
    }

    @Override
    public Account save(Account user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepository.save ( user );
    }

    @Override
    public Account getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById ( id );
    }

    @Override
    public List<AccountDTO> findAllUsersDTO() {
        return accountRepository.findAllUsersDTO ();
    }

    @Override
    public List<AccountDTO> findAllUsersDTODeleted() {
        return accountRepository.findAllUsersDTODeleted ();
    }

    @Override
    public List<AccountDTO> findAllUsersDTODeletedFalseAndActiveTrue() {
        return accountRepository.findAllUsersDTODeletedFalseAndActiveTrue ();
    }

    @Override
    public List<AccountDTO> findAllUsersDTODeletedFalseAndActiveFalse() {
        return accountRepository.findAllUsersDTODeletedFalseAndActiveFalse ();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return accountRepository.existsByUsername ( username );
    }

    @Override
    public Boolean existsByEmail(String email) {
        return accountRepository.existsByEmail ( email );
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return accountRepository.existsByPhone ( phone );
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<AccountDTO> findUserDTOByUsername(String username) {
        return accountRepository.findUserDTOByUsername(username);
    }

    @Override
    public Optional<Account> getByUsername(String username) {
        return accountRepository.getByUsername ( username );
    }

    @Override
    public void blockUser(Long userId) {
        accountRepository.blockUser ( userId );
    }

    @Override
    public Account findByBlockedIsFalseAndId(Long id) {
        return accountRepository.findByBlockedIsFalseAndId ( id );
    }

    @Override
    public void deleteData(Long userId) {
        accountRepository.deleteData ( userId );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (!accountOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return AccountPrinciple.build(accountOptional.get());
    }
}
