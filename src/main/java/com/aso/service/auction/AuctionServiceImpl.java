package com.aso.service.auction;

import com.aso.exception.*;
import com.aso.model.Auction;
import com.aso.model.Product;
import com.aso.model.dto.AuctionDTO;
import com.aso.repository.AuctionRepository;
import com.aso.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

    private static final int PAGE_SIZE = 20;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private BidRepository bidRepository;
    @Override
    public Auction createAuction(AuctionDTO auctionDTO) {
        Auction auction = auctionRepository.save(auctionDTO.toAuction());

        if (auction.getAuctionEndTime().isBefore(LocalDateTime.now())) {
            throw new IncorrectDateException("Thời gian kết thúc phiên đấu giá không được ở trong quá khứ!");
        }

        if (auction.getAuctionEndTime().minusDays(1).compareTo(auction.getAuctionStartTime()) < 0) {
            throw new IncorrectDateException(
                    "Thời gian kết thúc phiên đấu giá phải sau thời gian bắt đầu ít nhất 1 ngày!");
        }

        if (auction.getCurrentPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IncorrectPriceException("Giá tối thiểu không được âm!");
        }

        return auctionRepository.save(auction);
    }
    @Override
    public List<AuctionDTO> getAllAuctions() {
        return auctionRepository.getAllAuctions();
    }

    @Override
    public Iterable<Auction> findAll() {
        return null;
    }

    @Override
    public Optional<Auction> findById(Long id) {
        return auctionRepository.findById(id);
    }

    @Override
    public Auction save(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Auction getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(Auction auction) {
        auction.setDeleted(true);
        auctionRepository.save(auction);
    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

}

