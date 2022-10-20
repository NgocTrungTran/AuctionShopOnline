package com.aso.repository;

import com.aso.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByRelatedOfferId(long relatedOfferId);
}
