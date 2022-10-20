package com.aso.service.mapper;

import com.aso.model.Account;
import com.aso.model.Auction;
import com.aso.model.dto.AuctionRequest;
import java.time.LocalDateTime;

public class AuctionDtoMapper {

    private AuctionDtoMapper() {
    }

    public static Auction mapAuctionRequestToAuction(AuctionRequest auctionRequest) {
        return Auction.builder()
                .email(auctionRequest.getEmail())
                .auctionEndTime(LocalDateTime.now().plusDays(auctionRequest.getDaysToEndTime()))
                .itemStatus(auctionRequest.getItemStatus())
                .auctionType(auctionRequest.getAuctionType())
                .startingPrice(auctionRequest.getStartingPrice())
                .currentPrice(auctionRequest.getStartingPrice())
                .auctionStartTime(LocalDateTime.now())
                .account(auctionRequest.getAccount().toAccount())
                .product(auctionRequest.getProduct().toProduct())
                .build();
    }

}
