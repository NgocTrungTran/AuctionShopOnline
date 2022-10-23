package com.aso.service.auction;

import com.aso.model.Auction;
import com.aso.model.dto.AuctionRequest;

import java.util.List;

public interface AuctionService {

    Auction createAuction(AuctionRequest auctionRequest);

    Auction deleteAuction(Long id);

    Auction updateAuction(Long id, AuctionRequest auctionRequest);

    List<AuctionRequest> getAllAuctions();
}

