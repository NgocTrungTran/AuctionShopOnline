package com.aso.service.bid;

import com.aso.events.MailSenderPublisher;
import com.aso.exception.*;
import com.aso.model.Auction;
import com.aso.model.Product;
import com.aso.model.enums.AuctionType;
import com.aso.model.Bid;
import com.aso.model.dto.BidDTO;
import com.aso.repository.AuctionRepository;
import com.aso.repository.BidRepository;
import com.aso.service.mapper.BidDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BidServiceImpl implements BidService {
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private MailSenderPublisher mailSenderPublisher;
    @Override
    public Bid createBid(BidDTO bidDTO, Long auctionId) {
        Bid bid = BidDtoMapper.mapToBid(bidDTO, auctionId);
        long relatedOfferId = bid.getRelatedOfferId();

        Bid savedBid = null;

        Auction auction = auctionRepository.findById(relatedOfferId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Phiếu mua hàng có id " + relatedOfferId + " không tồn tại!"));

        if (auction.getAuctionType().equals(AuctionType.BUY_NOW)) {
            throw new IncorrectAuctionTypeException("Không thể đặt giá thầu trên đấu giá mua ngay bây giờ!");
        }
        if (bid.getBidPrice().compareTo(auction.getCurrentPrice()) <= 0) {
            throw new IncorrectPriceException("\n" +
                    "Giá dự thầu phải lớn hơn giá chào!");
        }
        if (LocalDateTime.now().isAfter(auction.getAuctionEndTime())) {
            throw new IncorrectDateException("Phiên đấu giá đã kết thúc!");
        }

        auction.setCurrentPrice(bid.getBidPrice());
        auction.setAuctionType(AuctionType.BIDDING);
        auctionRepository.save(auction);
        savedBid = bidRepository.save(bid);

        List<Bid> bidsForGivenOffer = bidRepository.findByRelatedOfferId(relatedOfferId);
        List<String> emailBids =
                bidsForGivenOffer.stream().map(Bid::getEmail).collect(Collectors.toList());
        mailSenderPublisher.publishNewBid(emailBids, relatedOfferId, bid.getBidPrice());
        return savedBid;
    }

    @Override
    public Bid deleteBid(Long auctionId, Long bidId) {

        Bid bidToDelete = bidRepository.findById(bidId).orElseThrow(
                () -> new ResourceNotFoundException("Bid with id " + bidId + " không tồn tại!"));
        List<Bid> bidsForGivenOffer = bidRepository.findByRelatedOfferId(auctionId);
        Bid highestPriceBid =
                bidsForGivenOffer.stream().max(Comparator.comparing(Bid::getBidPrice)).get();

        if (!bidToDelete.getEmail().equals(highestPriceBid.getEmail())) {
            throw new IncorrectOperationException("Bạn chỉ có thể xóa giá thầu của chính mình!");
        }

        if (highestPriceBid.getId() != bidId) {
            throw new IncorrectOperationException("\n" +
                    "Bạn chỉ có thể xóa giá thầu với giá cao nhất");
        }

        Auction auctionToChangePrice = auctionRepository.findById(auctionId).orElseThrow(
                () -> new ResourceNotFoundException("Đấu giá có id " + auctionId + " không tồn tại!"));

        if (bidsForGivenOffer.size() == 1) {
            auctionToChangePrice.setCurrentPrice(auctionToChangePrice.getStartingPrice());
        } else {
            auctionToChangePrice.setCurrentPrice(highestPriceBid.getBidPrice());
        }

        List<String> emailBids =
                bidsForGivenOffer.stream().map(Bid::getEmail).collect(Collectors.toList());

        mailSenderPublisher.publishDeletedBid(emailBids, auctionId, highestPriceBid.getBidPrice());
        bidRepository.delete(bidToDelete);
        return bidToDelete;
    }

    @Override
    public Iterable<Bid> findAll() {
        return null;
    }

    @Override
    public Optional<Bid> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Bid save(Bid bid) {
        return null;
    }

    @Override
    public Bid getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(Bid bid) {

    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }
}
