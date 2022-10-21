package com.aso.controller.api;

import com.aso.events.MailSenderPublisher;
import com.aso.exception.IncorrectDateException;
import com.aso.exception.IncorrectOperationException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Auction;
import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;
import com.aso.repository.AuctionRepository;
import com.aso.repository.BidRepository;
import com.aso.service.bid.BidService;
import com.aso.service.bid.BidServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bids")
public class BidAPI {
    @Autowired
    private BidService bidService;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private MailSenderPublisher mailSenderPublisher;

    @PostMapping("/{auctionId}/bids")
    public ResponseEntity<?> createBid(@PathVariable Long auctionId,
                                         @RequestBody @Valid BidDTO bidDTO) {

        return new ResponseEntity<>(bidService.createBid(bidDTO, auctionId).toBidDTO(),HttpStatus.CREATED);
    }

    @PutMapping("/delete-soft/{id}")
    public ResponseEntity<?> doDelete(@PathVariable Long auctionId, Long bidId) {

        Bid bidToDelete = bidRepository.findById(bidId).orElseThrow(
                () -> new ResourceNotFoundException("Bid with id " + bidId + " không tồn tại!"));
//        List<Bid> bidsForGivenOffer = bidRepository.findByRelatedOfferId(auctionId);
//        Bid highestPriceBid =
//                bidsForGivenOffer.stream().max(Comparator.comparing(Bid::getBidPrice)).get();

//        if (!bidToDelete.getEmail().equals(highestPriceBid.getEmail())) {
//            throw new IncorrectOperationException("Bạn chỉ có thể xóa giá thầu của chính mình!");
//        }
//
//        if (highestPriceBid.getId() != bidId) {
//            throw new IncorrectOperationException(
//                    "Bạn chỉ có thể xóa giá thầu với giá cao nhất");
//        }

        Auction auctionToChangePrice = auctionRepository.findById(auctionId).orElseThrow(
                () -> new ResourceNotFoundException("Đấu giá có id " + auctionId + " không tồn tại!"));

//        if (bidsForGivenOffer.size() == 1) {
//            auctionToChangePrice.setCurrentPrice(auctionToChangePrice.getStartingPrice());
//        } else {
//            auctionToChangePrice.setCurrentPrice(highestPriceBid.getBidPrice());
//        }

        return new ResponseEntity<>("Đã xóa thành công!", HttpStatus.OK);
    }

}
