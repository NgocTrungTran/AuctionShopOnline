package com.aso.service.account;

import com.aso.model.Account;
import com.aso.model.dto.AccountDTO;
import com.aso.model.dto.CategoryDTO;
import com.aso.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.List;
import java.util.Optional;

public interface AccountService extends IGeneralService<Account>, UserDetailsService {

    List<AccountDTO> findAllAccountsDTO();

    List<AccountDTO> findAllUsersDTODeleted();

    List<AccountDTO> findAllUsersDTODeletedFalseAndActiveTrue();

    List<AccountDTO> findAllUsersDTODeletedFalseAndActiveFalse();


    Optional<Account> findByIdAndDeletedFalse(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);


    Optional<Account> findByUsername(String username);

    Optional<AccountDTO> findUserDTOByUsername(String username);
    Optional<AccountDTO> findUserDTOById(Long id);


    Optional<Account> getByUsername(String username);

    Account create(AccountDTO newAccount);


    void blockUser(Long userId);

    void unblockUser(Long userId);

    Account findByBlockedIsFalseAndId(Long id);

    void deleteData(Long userId);

    Account doCreate(AccountDTO accountDTO);

}
