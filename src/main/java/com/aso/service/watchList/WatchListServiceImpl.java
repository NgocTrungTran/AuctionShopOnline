package com.aso.service.watchList;

import com.aso.model.Account;
import com.aso.model.Product;
import com.aso.model.WatchList;
import com.aso.model.dto.WatchListDTO;
import com.aso.repository.AccountRepository;
import com.aso.repository.ProductRepository;
import com.aso.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class WatchListServiceImpl implements WatchListService{
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Iterable<WatchList> findAll() {
        return null;
    }

    @Override
    public Optional<WatchList> findById(Long id) {
        return watchListRepository.findById ( id );
    }

    @Override
    public void removeById(WatchList watchList) {

    }

    @Override
    public WatchList save(WatchList watchList) {
        return watchListRepository.save ( watchList );
    }

    @Override
    public WatchList getById(Long id) {
        return watchListRepository.getById ( id );
    }

    @Override
    public void softDelete(WatchList watchList) {

    }

    @Override
    public void delete(Product id) {

    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public WatchListDTO doAddWatchList(Account account, Product product) {
        WatchList watchList = new WatchList ();
        watchList.setAccount ( account );
        watchList.setProduct ( product );

        WatchList newWatchList = watchListRepository.save ( watchList );
        return newWatchList.toWatchListDTO ();
    }
}
