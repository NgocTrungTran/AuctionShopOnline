package com.aso.controller.api;

import com.aso.exception.DataInputException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Auction;
import com.aso.model.Product;
import com.aso.model.dto.AuctionDTO;
import com.aso.model.dto.ProductDTO;
import com.aso.service.auction.AuctionService;
import com.aso.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auctions")
public class AuctionAPI {
    @Autowired
    AuctionService auctionService;

    @Autowired
    AppUtil appUtil;

    @GetMapping
    public List<AuctionDTO> getAllItems() {
        return auctionService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<Boolean> postNewAuction(@RequestBody AuctionDTO auctionDTO) {
        boolean isSaved = auctionService.postNewAuction(auctionDTO);
        return ResponseEntity.ok(isSaved);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<?> getProductById(@PathVariable String auctionId) {
        Long auction_id = Long.parseLong(auctionId);
        Optional<Auction> auctionOptional = auctionService.findById(auction_id);
        return new ResponseEntity<>(auctionOptional.get().toAuctionDTO(), HttpStatus.OK);
    }
}
