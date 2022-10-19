package com.aso.service.bid;

import com.aso.model.Bid;
import com.aso.service.IGeneralService;

import java.util.List;

public interface BidService extends IGeneralService<Bid> {
    List<Bid> getAllBids();
    Bid postNewBid(Bid bid);
    List<Bid> findBidsByAuctionId(long auctionID);
}
