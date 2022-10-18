package auction.services;

import auction.repositories.BidRepo;
import auction.dtos.SocketDTO;
import auction.entities.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    @Autowired
    BidRepo bidRepo;

    @Autowired
    SocketService socketService;

    public List<Bid> getAllBids() {
        return bidRepo.findAll();
    }

    public Optional<Bid> getBidById(Long id) {
        return bidRepo.findById(id);
    }

    public List<Bid> findBidsByAuctionId(long auctionID) {
        return bidRepo.findBidsByAuctionId(auctionID);
    }

    public Bid postNewBid(Bid bid) {
        Bid savedBid = bidRepo.save(bid);
        SocketDTO socketData = new SocketDTO("bid", savedBid);
        socketService.sendToAllClient(socketData);
        return savedBid;
    }

}

