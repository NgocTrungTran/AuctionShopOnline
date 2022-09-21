package com.aso.service.account;

import com.aso.model.Account;
import com.aso.model.dto.AccountDTO;
import com.aso.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AccountService extends IGeneralService<Account>, UserDetailsService {

    List<AccountDTO> findAllUsersDTO();

    List<AccountDTO> findAllUsersDTODeleted();

    List<AccountDTO> findAllUsersDTODeletedFalseAndActiveTrue();

    List<AccountDTO> findAllUsersDTODeletedFalseAndActiveFalse();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);


    Optional<Account> findByUsername(String username);

    Optional<AccountDTO> findUserDTOByUsername(String username);

    Optional<Account> getByUsername(String username);

    void blockUser(Long userId);

    Account findByBlockedIsFalseAndId(Long id);

    void deleteData(Long userId);
}
