package com.aso.service.bid;


import com.aso.model.Auction;
import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;
import com.aso.service.IGeneralService;

public interface BidService extends IGeneralService<Bid> {

    Bid createBid(BidDTO bidDTO, Long auctionId);

    void softDelete(Bid bid, Auction auction);

    void softDelete(Bid bid);
}
