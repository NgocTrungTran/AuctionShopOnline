package com.aso.service.bid;

import com.aso.model.Bid;
import com.aso.model.Product;
import com.aso.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BidServiceImpl implements  BidService {
    @Autowired
    BidRepository bidRepository;

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
    @Override
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }
    public Bid postNewBid(Bid bid) {
        Bid savedBid = bidRepository.save(bid);
//        SocketDTO socketData = new SocketDTO("bid", savedBid);
//        socketService.sendToAllClient(socketData);
        return savedBid;
    }
    public List<Bid> findBidsByAuctionId(long auctionID) {
        return bidRepository.findBidsByAuctionId(auctionID);
    }


}
