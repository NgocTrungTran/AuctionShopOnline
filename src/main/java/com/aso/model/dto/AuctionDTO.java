package com.aso.model.dto;


import com.aso.model.Account;
import com.aso.model.Auction;
import com.aso.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class AuctionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    private String createdBy;

    private Date updateAt;

    private String updateBy;

    private AccountDTO account;

    private ProductDTO product;

    private BigDecimal initial_price;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal buyOutPrice;

    private long start_date;

    private long stop_date;

    public AuctionDTO(Long id, Date createdAt, String createdBy, Date updateAt, String updateBy, Account account, Product product, BigDecimal initial_price, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal buyOutPrice, long start_date, long stop_date) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.account = account.toAccountDTO();
        this.product = product.toProductDTO();
        this.initial_price = initial_price;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.buyOutPrice = buyOutPrice;
        this.start_date = start_date;
        this.stop_date = stop_date;
    }
    public Auction toAuction(){
        return  new Auction()
                .setId(id)
                .setAccount(account.toAccount())
                .setProduct(product.toProduct())
                .setInitial_price(initial_price)
                .setMinPrice(minPrice)
                .setMaxPrice(maxPrice)
                .setBuyOutPrice(buyOutPrice)
                .setStart_date(start_date)
                .setStop_date(stop_date);
    }

}
