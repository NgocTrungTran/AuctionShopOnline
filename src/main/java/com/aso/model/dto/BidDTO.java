package com.aso.model.dto;

import com.aso.model.Account;
import com.aso.model.Auction;
import com.aso.model.Bid;
import com.aso.utils.PriceConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BidDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "sample@mail.com")
    @Email(regexp = "[^@]+@[^@]+\\.[^@.]+", message = "Email is not valid")
    private String email;

    @PriceConstraint
    private BigDecimal bidPrice;

    private AccountDTO account;

    private AuctionDTO auction;

    private boolean deleted = false;

    private LocalDateTime bidTime;

    public BidDTO(Long id, String email, BigDecimal bidPrice, Account account, Auction auction, boolean deleted, LocalDateTime bidTime) {
        this.id = id;
        this.email = email;
        this.bidPrice = bidPrice;
        this.account = account.toAccountDTO();
        this.auction = auction.toAuctionDTO();
        this.deleted = deleted;
        this.bidTime = bidTime;
    }

    public Bid toBid(){
        return new Bid()
                .setId(id)
                .setEmail(email)
                .setBidPrice(bidPrice)
                .setAccount(account.toAccount())
                .setAuction(auction.toAuction())
                .setBidTime(bidTime)
                .setDeleted(deleted)
                ;
    }
}
