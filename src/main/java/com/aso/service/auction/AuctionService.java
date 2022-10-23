package com.aso.service.auction;

import com.aso.model.Auction;
import com.aso.model.dto.AuctionDTO;
import com.aso.service.IGeneralService;

import java.util.List;

public interface AuctionService extends IGeneralService<Auction> {

    Auction createAuction(AuctionDTO auctionDTO);

    List<AuctionDTO> getAllAuctions();

    void softDelete(Auction auction);

}

