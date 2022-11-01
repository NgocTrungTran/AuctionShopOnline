package com.aso.controller.api;

import com.aso.exception.AccountInputException;
import com.aso.exception.DataInputException;
import com.aso.model.Account;
import com.aso.model.Product;
import com.aso.model.dto.WatchListDTO;
import com.aso.service.account.AccountService;
import com.aso.service.product.ProductService;
import com.aso.service.watchList.WatchListService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/watch-lists")
public class WatchListAPI {
    @Autowired
    private WatchListService watchListService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add/{accountId}")
    public ResponseEntity<?> addWatchedList(@PathVariable("accountId") Long accountId, Product product) {
        try {
            Optional<Account> accountOptional = accountService.findById ( accountId );
            if ( accountOptional.isEmpty () ) {
                throw new AccountInputException ( "Tài khoản không tồn tại" );
            }

            Optional<Product> productOptional = productService.findProductBySlug ( product.getSlug () ) ;
            if (productOptional.isEmpty ()) {
                throw new DataInputException ( "Sản phẩm không tồn tại" );
            }

            WatchListDTO watchListDTO = watchListService.doAddWatchList ( accountOptional.get (), productOptional.get () );

            return new ResponseEntity<> ( watchListDTO, HttpStatus.CREATED );
        }catch (Exception e) {
            return new ResponseEntity<> (e.getMessage (), HttpStatus.BAD_GATEWAY);
        }
    }
}
