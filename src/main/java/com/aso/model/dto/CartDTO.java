package com.aso.model.dto;


import com.aso.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartDTO {

    private Long id;

    private String status;

    private AccountDTO account;


    public CartDTO(Long id, Account account){
        this.id = id;
        this.account = account.toAccountDTO();
    }

    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setAccount(account.toAccount())
               ;
    }
}
