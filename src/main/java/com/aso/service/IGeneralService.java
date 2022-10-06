package com.aso.service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    T getById(Long id);
    void softDelete(T t);
    void delete(Long id);
    Boolean existById(Long id);
}
