package com.aso.model.dto;

import com.aso.model.Account;
import com.aso.model.Product;
import com.aso.model.WatchList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WatchListDTO {
    private Long id;

    private Account account;

    private Product product;

    private Date createdAt;

    public WatchList toWatchList(){
        return new WatchList ()
                .setId ( id )
                .setAccount ( account )
                .setProduct ( product )
                .setCreatedAt ( createdAt );
    }
}
