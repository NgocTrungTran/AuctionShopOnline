package com.aso.controller.api;

import com.aso.model.Bid;
import com.aso.service.bid.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidAPI {
    @Autowired
    BidService bidService;

    @GetMapping("/api/bids")
    public List<Bid> getAllBids() {
        return bidService.getAllBids();
    }
    @PostMapping("/api/bids")
    public Bid postNewBid(@RequestBody Bid bid) {
        return bidService.postNewBid(bid);
    }
    @GetMapping("/api/bids/{auctionId}")
    public ResponseEntity<List<Bid>> getAllBidsByAuctionId(@PathVariable long auctionId) {
        List<Bid> bids = bidService.findBidsByAuctionId(auctionId);
        return ResponseEntity.ok(bids);
    }
}
