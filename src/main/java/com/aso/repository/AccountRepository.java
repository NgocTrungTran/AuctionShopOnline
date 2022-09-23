package com.aso.repository;

import com.aso.model.Account;
import com.aso.model.dto.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> getByUsername(String username);
    @Query("SELECT new com.aso.model.dto.AccountDTO (" +
            "a.id, " +
            "a.username, " +
            "a.fullName, " +
            "a.email, " +
            "a.phone, " +
            "a.avatar, " +
            "a.blocked, " +
            "a.locationRegion" +
            ") " +
            "FROM Account AS a WHERE a.deleted = false and a.role.id = 2"
    )
    List<AccountDTO> findAllUsersDTO();

    @Query("SELECT new com.aso.model.dto.AccountDTO (" +
            "a.id, " +
            "a.username, " +
            "a.fullName, " +
            "a.email, " +
            "a.phone, " +
            "a.avatar, " +
            "a.blocked, " +
            "a.locationRegion" +
            ") " +
            "FROM Account AS a WHERE a.deleted = true and a.role.id = 2"
    )
    List<AccountDTO> findAllUsersDTODeleted();

    @Query("SELECT new com.aso.model.dto.AccountDTO (" +
            "a.id, " +
            "a.username, " +
            "a.fullName, " +
            "a.email, " +
            "a.phone, " +
            "a.avatar, " +
            "a.blocked, " +
            "a.locationRegion" +
            ") " +
            "FROM Account AS a WHERE a.deleted = false and a.role.id = 2 and a.blocked = false "
    )
    List<AccountDTO> findAllUsersDTODeletedFalseAndActiveTrue();

    @Query("SELECT new com.aso.model.dto.AccountDTO (" +
            "a.id, " +
            "a.username, " +
            "a.fullName, " +
            "a.email, " +
            "a.phone, " +
            "a.avatar, " +
            "a.blocked, " +
            "a.locationRegion" +
            ") " +
            "FROM Account AS a WHERE a.deleted = false and a.role.id = 2 and a.blocked = true "
    )
    List<AccountDTO> findAllUsersDTODeletedFalseAndActiveFalse();

    Optional<Account> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    Account findByBlockedIsFalseAndId(Long id);

    @Query("SELECT NEW com.aso.model.dto.AccountDTO (" +
            "a.id, " +
            "a.username" +
            ") " +
            "FROM Account AS a " +
            "WHERE a.username = ?1"
    )
    Optional<AccountDTO> findUserDTOByUsername(String username);

    @Modifying
    @Query("UPDATE Account AS a " +
            "SET a.blocked = 1 " +
            "WHERE a.id = :userId")
    void blockUser(@Param("userId") Long userId);
    @Modifying
    @Query("DELETE FROM Account AS a WHERE (a.id = :userId)")
    void deleteData(@Param("userId") Long userId);
}
