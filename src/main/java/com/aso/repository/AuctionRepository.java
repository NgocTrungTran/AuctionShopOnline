package com.aso.repository;

import com.aso.model.Auction;
import com.aso.model.dto.AuctionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query("SELECT NEW com.aso.model.dto.AuctionDTO (" +
            "a.id, " +
            "a.createdAt, " +
            "a.createdBy, " +
            "a.updatedAt, " +
            "a.updatedBy, " +
            "a.email, " +
            "a.account, " +
            "a.product, " +
            "a.auctionType, " +
            "a.itemStatus, " +
            "a.startingPrice, " +
            "a.currentPrice, " +
            "a.auctionEndTime, " +
            "a.auctionStartTime, " +
            "a.daysToEndTime" +
            ") " +
            "FROM Auction AS a WHERE a.deleted = false ")
    List<AuctionDTO> getAllAuctions();


}
