package com.aso.service.auction;

import com.aso.model.Auction;
import com.aso.model.Product;
import com.aso.model.dto.AuctionDTO;
import com.aso.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService{
    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public Iterable<Auction> findAll() {
        return null;
    }

    @Override
    public Optional<Auction> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Auction save(Auction auction) {
        return null;
    }

    @Override
    public Auction getById(Long id) {
        return null;
    }

    @Override
    public void softDelete(Auction auction) {

    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public List<AuctionDTO> getAllItems() {
        return auctionRepository.getAllItems();
    }
    @Override
    public boolean postNewAuction(AuctionDTO auctionDTO) {
//        AuctionDTO auctionDTO1 = auctionRepository.save(auctionDTO);
//        SocketDTO socketData = new SocketDTO("auction", auctionDTO1);
//        socketService.sendToAllClient(socketData);
//        return auctionDTO1.getId() > 0;
        return false;
    }

    @Override
    public Optional<Auction> getOneItem(Long id) {
        return auctionRepository.findById(id);
    }
}
