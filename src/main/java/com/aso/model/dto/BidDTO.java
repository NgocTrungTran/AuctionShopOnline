package com.aso.model.dto;

import com.aso.model.Account;
import com.aso.model.Auction;
import com.aso.model.BaseEntity;
import com.aso.model.Bid;
import com.aso.utils.PriceConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BidDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private String createdBy;

    @Schema(example = "sample@mail.com")
    @Email(regexp = "[^@]+@[^@]+\\.[^@.]+", message = "Email is not valid")
    private String email;
    @PriceConstraint
    private BigDecimal bidPrice;

    private AccountDTO account;

    private AuctionDTO auction;

    private boolean deleted = false;


    public BidDTO(Long id, Date createdAt, String createdBy, String email, BigDecimal bidPrice, Account account, Auction auction, boolean deleted) {
        this.id = id;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.email = email;
        this.bidPrice = bidPrice;
        this.account = account.toAccountDTO();
        this.auction = auction.toAuctionDTO();
        this.deleted = deleted;
    }

    public Bid toBid(){
        return new Bid()
                .setId(id)
                .setEmail(email)
                .setBidPrice(bidPrice)
                .setAccount(account.toAccount())
                .setAuction(auction.toAuction())
                .setDeleted(deleted)
                ;
    }
}
