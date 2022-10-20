package com.aso.controller.api;

import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;
import com.aso.service.bid.BidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bids")
public class BidAPI {
    @Autowired
    private BidServiceImpl bidServiceImpl;

    @PostMapping("/{id}/bids")
    public ResponseEntity<Bid> createBid(@PathVariable Long id,
                                         @RequestBody @Valid BidDTO bidDTO) {

        return new ResponseEntity<>(bidServiceImpl.createBid(bidDTO, id),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/bids/{bidId}")
    public ResponseEntity<Bid> deleteBid(@PathVariable Long id,
                                         @PathVariable Long bidId) {

        return new ResponseEntity<>(bidServiceImpl.deleteBid(id, bidId), HttpStatus.OK);
    }

}
