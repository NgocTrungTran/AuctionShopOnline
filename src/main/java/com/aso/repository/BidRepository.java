package com.aso.repository;


import com.aso.model.Bid;
import com.aso.model.dto.AuctionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query(value = "SELECT * FROM bids WHERE auction_id = :auctionID", nativeQuery = true)
    List<Bid> findBidsByAuctionId(@Param("auctionID") long auctionID);

//    @Query("SELECT NEW com.aso.model.dto.BidDTO (" +
//            "b.id, " +
//            "b.createdAt, " +
//            "b.createdBy, " +
//            "b.updatedAt, " +
//            "b.updatedBy, " +
//            "b.account, " +
//            "b.auction, " +
//            "b.auctionPrice " +
//            ") " +
//            "FROM Bid AS b WHERE b.deleted = false")
//    List<BidDTO> findAll();
}
