package com.aso.repository;

import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

     @Query("SELECT NEW com.aso.model.dto.BidDTO (" +
             "b.id, " +
             "b.email, " +
             "b.bidPrice, " +
             "b.account, " +
             "b.auction, " +
             "b.deleted, " +
             "b.bidTime " +
             ") " +
             "FROM Bid AS b WHERE b.deleted = false ")
     List<BidDTO> getAllBids();

     @Query("SELECT NEW com.aso.model.dto.BidDTO (" +
             "b.id, " +
             "b.email, " +
             "b.bidPrice, " +
             "b.account, " +
             "b.auction, " +
             "b.deleted, " +
             "b.bidTime " +
             ") " +
             "FROM Bid AS b WHERE b.deleted = false AND b.bidPrice = 0")
     List<BidDTO> getAllBidsByBidPrice(Long id);


     @Query("SELECT NEW com.aso.model.dto.BidDTO (" +
             "b.id, " +
             "b.email, " +
             "b.bidPrice, " +
             "b.account, " +
             "b.auction, " +
             "b.deleted, " +
             "b.bidTime " +
             ") " +
             "FROM Bid AS b WHERE b.deleted = false AND b.auction.id = ?1 ")
     List<BidDTO> findByRelatedOfferId(long id);
}
