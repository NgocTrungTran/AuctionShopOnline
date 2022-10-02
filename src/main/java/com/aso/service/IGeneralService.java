package com.aso.service;

import com.aso.model.Account;
import com.aso.model.dto.AccountDTO;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    T getById(Long id);

    void delete(Long id);

}
