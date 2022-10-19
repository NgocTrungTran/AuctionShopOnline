package com.aso.service.auction;

import com.aso.model.Auction;
import com.aso.model.dto.AuctionDTO;
import com.aso.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface AuctionService extends IGeneralService<Auction> {

    List<AuctionDTO> getAllItems();
    boolean postNewAuction(AuctionDTO auctionDTO);

    Optional<Auction> getOneItem(Long id);

}
