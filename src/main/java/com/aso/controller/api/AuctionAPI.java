package com.aso.controller.api;

import com.aso.exception.*;
import com.aso.model.Auction;
import com.aso.model.dto.AuctionDTO;
import com.aso.repository.BidRepository;
import com.aso.service.auction.AuctionService;
import com.aso.service.bid.BidService;
import com.aso.utils.AppUtil;
import com.aso.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auctions")
public class AuctionAPI {

    @Autowired
    private AppUtil appUtil;
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidService bidService;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private Validation validation;

    @PostMapping("")
    public ResponseEntity<Auction> createAuction(
            @RequestBody @Valid AuctionDTO auctionDTO) {
        return new ResponseEntity<>(auctionService.createAuction(auctionDTO),
                HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAuctions() {
        List<AuctionDTO> auctionDTOS = auctionService.getAllAuctions();

        if (auctionDTOS.isEmpty()) {
            throw new DataOutputException("No data");
        }

        return new ResponseEntity<>(auctionDTOS, HttpStatus.OK);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<?> getAuctionById(@PathVariable String auctionId) {

        if (!validation.isIntValid(auctionId)) {
            throw new DataInputException("Auction ID invalid!");
        }

        Long auction_id = Long.parseLong(auctionId);
        Optional<Auction> auctionOptional = auctionService.findById(auction_id);

        if (!auctionOptional.isPresent()) {
            throw new ResourceNotFoundException("Auction invalid!");
        }
        return new ResponseEntity<>(auctionOptional.get().toAuctionDTO(), HttpStatus.OK);
    }

    @PutMapping("/delete-soft/{id}")
    public ResponseEntity<?> doDelete(@PathVariable Long id) {

        Auction auctionToDelete = auctionService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Phiếu mua hàng có id " + id + " không tồn tại!"));

        if (auctionToDelete.getAuctionEndTime().isBefore(LocalDateTime.now())) {
            throw new IncorrectDateException(
                    "Không thể xóa phiên đấu giá đã kết thúc!");
        }
//        long bidsForAuctionCount = bidRepository.findByRelatedOfferId(id).size();
//
//        if (bidsForAuctionCount != 0) {
//            throw new IncorrectOperationException(
//                    "Không thể xóa đấu giá có giá thầu!");
//        }
        auctionService.softDelete(auctionToDelete);
        return new ResponseEntity<>("Đã xóa thành công!", HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> doEdit(@PathVariable Long id, @Validated @RequestBody AuctionDTO auctionDTO,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<Auction> auction = auctionService.findById(id);
        if (!auction.isPresent()) {
            return new ResponseEntity<>("Không tồn tại sản phẩm!", HttpStatus.NOT_FOUND);
        }

        try {
            auction.get().setUpdatedAt(new Date());
            auction.get().setEmail(auctionDTO.getEmail());
            auction.get().setAccount(auctionDTO.getAccount().toAccount());
            auction.get().setProduct(auctionDTO.getProduct().toProduct());
            auction.get().setAuctionType(auctionDTO.getAuctionType());
            auction.get().setItemStatus(auctionDTO.getItemStatus());
            auction.get().setStartingPrice(auctionDTO.getStartingPrice());
            auction.get().setCurrentPrice(auctionDTO.getCurrentPrice());
            auction.get().setAuctionEndTime(auctionDTO.getAuctionEndTime());
            auction.get().setAuctionStartTime(auctionDTO.getAuctionStartTime());
            auction.get().setDaysToEndTime(auctionDTO.getDaysToEndTime());

            Auction newAuction = auctionService.save(auction.get());

            return new ResponseEntity<>(newAuction.toAuctionDTO(), HttpStatus.OK); // Lỗi ở đoạn này

        } catch (Exception e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Auction> updateAuction(@RequestBody @Valid AuctionDTO auctionDTO,
                                                 @PathVariable Long id) {
        return new ResponseEntity<>(auctionService.updateAuction(id, auctionDTO),
                HttpStatus.OK);
    }
}
