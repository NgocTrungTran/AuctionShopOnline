package com.aso.service.mapper;

import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;

import java.time.LocalDateTime;

public class BidDtoMapper {

    private BidDtoMapper() {
    }

    public static BidDTO mapToBidRequest(Bid bid) {
        return BidDTO.builder()
                .bidPrice(bid.getBidPrice())
                .email(bid.getEmail())
                .build();
    }

    public static Bid mapToBid(BidDTO bidDTO, long relatedOfferId) {

        return Bid.builder()
                .bidPrice(bidDTO.getBidPrice())
                .email(bidDTO.getEmail())
                .relatedOfferId(relatedOfferId)
                .bidTime(LocalDateTime.now())
                .build();
    }
}
