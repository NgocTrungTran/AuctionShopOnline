package com.aso.controller.api;

import com.aso.exception.DataOutputException;
import com.aso.model.Auction;
import com.aso.model.dto.AuctionRequest;
import com.aso.model.dto.ProductDTO;
import com.aso.service.auction.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionAPI {

    @Autowired
    private AuctionService auctionService;

    @PostMapping("")
    public ResponseEntity<Auction> createAuction(
            @RequestBody @Valid AuctionRequest auctionRequest) {
        return new ResponseEntity<>(auctionService.createAuction(auctionRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@RequestBody @Valid AuctionRequest auctionRequest,
                                                 @PathVariable Long id) {
        return new ResponseEntity<>(auctionService.updateAuction(id, auctionRequest),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Auction> deleteAuction(@PathVariable Long id) {
        return new ResponseEntity<>(auctionService.deleteAuction(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAuctions() {
        List<AuctionRequest> auctionRequests = auctionService.getAllAuctions();

        if (auctionRequests.isEmpty()) {
            throw new DataOutputException("No data");
        }

        return new ResponseEntity<>(auctionRequests, HttpStatus.OK);
    }
}
