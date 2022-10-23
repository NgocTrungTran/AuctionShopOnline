package com.aso.controller.api;

import com.aso.exception.*;
import com.aso.model.Auction;
import com.aso.model.Bid;
import com.aso.model.dto.AuctionDTO;
import com.aso.model.dto.BidDTO;
import com.aso.repository.AuctionRepository;
import com.aso.repository.BidRepository;
import com.aso.service.bid.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/bids")
public class BidAPI {
    @Autowired
    private BidService bidService;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;


    @PostMapping("/{auctionId}/bids")
    public ResponseEntity<?> createBid(@PathVariable Long auctionId,
                                       @RequestBody @Valid BidDTO bidDTO) {

        return new ResponseEntity<>(bidService.createBid(bidDTO, auctionId).toBidDTO(), HttpStatus.CREATED);
    }

    @PutMapping("/delete-soft/{bidId}")
    public ResponseEntity<Bid> deleteBid( @PathVariable Long bidId) {

        Bid bidToDelete = bidRepository.findById(bidId).orElseThrow(
                () -> new ResourceNotFoundException("Phiếu đấu giá có id " + bidId + " không tồn tại!"));

        List<BidDTO> bidsForGivenOffer = bidRepository.findByRelatedOfferId(bidToDelete.getAuction().getId());
        BidDTO highestPriceBid =
                bidsForGivenOffer.stream().max(Comparator.comparing(BidDTO::getBidPrice)).get();

        if (!bidToDelete.getEmail().equals(highestPriceBid.getEmail())) {
            throw new IncorrectOperationException("Bạn chỉ có thể xóa giá thầu của chính mình!");
        }

        if (highestPriceBid.getId() != bidId) {
            throw new IncorrectOperationException("Bạn chỉ có thể xóa giá thầu với giá cao nhất!");
        }

        Auction auctionToChangePrice = auctionRepository.findById(bidToDelete.getAuction().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Phiên đấu giá có id " + bidToDelete.getAuction().getId() + " không tồn tại!"));

        if (bidsForGivenOffer.size() == 1) {
            auctionToChangePrice.setCurrentPrice(auctionToChangePrice.getStartingPrice());
        } else {
            auctionToChangePrice.setCurrentPrice(highestPriceBid.getBidPrice());
        }

        bidToDelete.setDeleted(true);
        bidRepository.save(bidToDelete);

        return new ResponseEntity<Bid>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllBids() {
        List<BidDTO> bids = bidService.getAllBids();
        if (bids.isEmpty()) {
            throw new DataOutputException("Danh sách đấu thầu trống!");
        }
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }
}
