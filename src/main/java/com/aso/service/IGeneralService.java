package com.aso.service;

<<<<<<< HEAD
import com.aso.model.Account;
import com.aso.model.dto.AccountDTO;

=======
import com.aso.model.Product;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
>>>>>>> phong-dev
import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    T getById(Long id);
<<<<<<< HEAD

    void delete(Long id);

=======
    void softDelete(T t);
    void delete(Product id);
    Boolean existById(Long id);
>>>>>>> phong-dev
}
