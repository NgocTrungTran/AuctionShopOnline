package com.aso.service.account;

import com.aso.model.Account;
import com.aso.model.AccountPrinciple;
import com.aso.model.LocationRegion;
import com.aso.model.Role;
import com.aso.model.Product;
import com.aso.model.dto.AccountDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.repository.AccountRepository;
import com.aso.service.gmail.MyConstants;
import com.aso.service.location.LocationRegionService;
import com.aso.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocationRegionService locationRegionService;
    @Autowired
    private RoleService roleService;

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll ();
    }
//
//    @Override
//    public Optional<Account> findById(Long id) {
//        return accountRepository.findById ( id );
//    }

    @Override
    public Account save(Account user) {
        user.setPassword ( passwordEncoder.encode ( user.getPassword () ) );
        return accountRepository.save ( user );
    }

    @Override
    public Account getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(Account account) {

    }

    @Override
    public void delete(Product id) {
        accountRepository.deleteById ( id );
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public List<AccountDTO> findAllAccountsDTO() {
        return accountRepository.findAllAccountsDTO();
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
    public Optional<Account> findByIdAndDeletedFalse(Long id) {
        return accountRepository.findByIdAndDeletedFalse(id);
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
        return accountRepository.findByUsername ( username );
    }

    @Override
    public Optional<AccountDTO> findUserDTOByUsername(String username) {
        return accountRepository.findUserDTOByUsername ( username );
    }
    @Override
    public Optional<AccountDTO> findUserDTOById(Long id) {
        return accountRepository.findUserDTOById ( id );
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void removeById(Account account) {

    }

    @Override
    public Optional<Account> getByUsername(String username) {
        return accountRepository.getByUsername ( username );
    }

    @Override
    public Account create(AccountDTO newAccount) {
        return null;
    }


    @Override
    public void blockUser(Long userId) {
        accountRepository.blockUser ( userId );

    }

    public void unblockUser(Long userId){
        accountRepository.unblockUser(userId);
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
        Optional<Account> accountOptional = accountRepository.findByUsername ( username );
        if ( !accountOptional.isPresent () ) {
            throw new UsernameNotFoundException ( username );
        }
        return AccountPrinciple.build ( accountOptional.get () );
    }

    @Override
    public Account doCreate(AccountDTO accountDTO) {
        Optional<Role> optionalRole = roleService.findById(accountDTO.getRole().getId());
        LocationRegion locationRegion = accountDTO.getLocationregion().toLocationRegion ();
        LocationRegion newLocationRegion = locationRegionService.save ( locationRegion );
        accountDTO.setRole ( optionalRole.get ().toRoleDTO () );
        accountDTO.setLocationregion( newLocationRegion.toLocationRegionDTO () );
        Account account = accountDTO.toAccountAllAttribute ();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom ( MyConstants.MY_EMAIL );
        message.setSubject ( "Chào mừng bạn đến với Auction Shop!" );
        message.setTo(account.getEmail ());
        message.setSubject("Dear " + account.getFullName ());
        message.setText("Cám ơn bạn đã tham gia và ủng hộ Auction Shop! \n" +
                "Chúc bạn có những trải nghiệm thật thú vị.");
        this.emailSender.send(message);
        return save(account);
    }

    @Override
    public List<AccountDTO> findAccountDTOAll() {
        return accountRepository.findAccountDTOAll();
    }

    @Override
    public Optional<AccountDTO> findAccountByIdDTO(Long id) {
        return accountRepository.findAccountByIdDTO(id);
    }

    @Override
    public Page<AccountDTO> findAllAccounts(Pageable pageable) {
        return accountRepository.findAllAccounts(pageable);
    }

    @Override
    public Page<AccountDTO> findAllAccountss(Pageable pageable, String keyword) {
        return accountRepository.findAllAccountss(pageable, keyword);
    }

    @Override
    public AccountDTO findAccountByCreatedBy(String createBy) {
        return accountRepository.findAccountByCreatedBy(createBy);
    }
}
