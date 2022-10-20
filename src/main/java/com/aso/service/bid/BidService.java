package com.aso.service.bid;


import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;
import com.aso.service.IGeneralService;

public interface BidService extends IGeneralService<Bid> {

    Bid createBid(BidDTO bidDTO, Long auctionId);

    Bid deleteBid(Long auctionId, Long bidId);

}
