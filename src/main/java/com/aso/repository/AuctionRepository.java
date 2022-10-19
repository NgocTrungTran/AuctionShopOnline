package com.aso.repository;

import com.aso.model.Auction;
import com.aso.model.dto.AuctionDTO;
import com.aso.model.dto.ProductListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("SELECT NEW com.aso.model.dto.AuctionDTO (" +
            "a.id, " +
            "a.createdAt, " +
            "a.createdBy, " +
            "a.updatedAt, " +
            "a.updatedBy, " +
            "a.account, " +
            "a.product, " +
            "a.initial_price, " +
            "a.minPrice, " +
            "a.maxPrice, " +
            "a.buyOutPrice, " +
            "a.start_date, " +
            "a.stop_date" +
            ") " +
            "FROM Auction AS a WHERE a.deleted = false")
    List<AuctionDTO> getAllItems();
}
