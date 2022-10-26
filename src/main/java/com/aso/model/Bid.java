package com.aso.model;

import com.aso.model.dto.BidDTO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "bids")
@Accessors(chain = true)
public class Bid extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;

    private String email;

    @Column(precision = 12, scale = 0)
    private BigDecimal bidPrice;


    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;

    public BidDTO toBidDTO(){
        return new BidDTO()
                .setId(id)
                .setEmail(email)
                .setBidPrice(bidPrice)
                .setAccount(account.toAccountDTO())
                .setAuction(auction.toAuctionDTO())
                .setDeleted(deleted)
                ;
    }
}
