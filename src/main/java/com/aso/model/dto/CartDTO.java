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

    private String content;

    private AccountDTO account;


    public CartDTO(Long id, String content, Account account){
        this.id = id;
        this.content = content;
        this.account = account.toAccountDTO();
    }

    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setContent(content)
                .setAccount(account.toAccount())
               ;
    }
}
