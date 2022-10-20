package com.aso.service.auction;

import com.aso.exception.IncorrectDateException;
import com.aso.exception.IncorrectOperationException;
import com.aso.exception.IncorrectPriceException;
import com.aso.exception.ResourceNotFoundException;
import com.aso.model.Auction;
import com.aso.model.dto.AuctionRequest;
import com.aso.repository.AuctionRepository;
import com.aso.repository.BidRepository;
import com.aso.service.mapper.AuctionDtoMapper;
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
    public Auction createAuction(AuctionRequest auctionRequest) {

        Auction auction = AuctionDtoMapper.mapAuctionRequestToAuction(auctionRequest);

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
    public Auction deleteAuction(Long id) {
        Auction auctionToDelete = auctionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Phiếu mua hàng có id " + id + " không tồn tại!"));

        if (auctionToDelete.getAuctionEndTime().isBefore(LocalDateTime.now())) {
            throw new IncorrectDateException("\n" +
                    "Không thể xóa phiên đấu giá đã kết thúc!");
        }
        long bidsForAuctionCount = bidRepository.findByRelatedOfferId(id).size();

        if (bidsForAuctionCount != 0) {
            throw new IncorrectOperationException("\n" +
                    "Không thể xóa đấu giá có giá thầu!");
        }
        auctionRepository.deleteById(id);
        return auctionToDelete;
    }

    @Override
    public Auction updateAuction(Long id, AuctionRequest auctionRequest) {
        Auction auctionToUpdate = auctionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Đấu giá có id " + id + " không tồn tại!"));

        Optional.ofNullable(auctionRequest.getEmail()).ifPresent(auctionToUpdate::setEmail);

        return auctionRepository.save(auctionToUpdate);
    }

    @Override
    public List<AuctionRequest> getAllAuctions() {
        return auctionRepository.getAllAuctions();
    }

}

