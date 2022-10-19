package com.aso.model;


import com.aso.model.dto.AuctionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="auctions")
@Accessors(chain = true)
public class Auction extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private BigDecimal initial_price;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal buyOutPrice;

    private long start_date;

    private long stop_date;

    public AuctionDTO toAuctionDTO(){
        return  new AuctionDTO()
                .setId(id)
                .setAccount(account.toAccountDTO())
                .setProduct(product.toProductDTO())
                .setInitial_price(initial_price)
                .setMinPrice(minPrice)
                .setMaxPrice(maxPrice)
                .setBuyOutPrice(buyOutPrice)
                .setStart_date(start_date)
                .setStop_date(stop_date);
    }
}
