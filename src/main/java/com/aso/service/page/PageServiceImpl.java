package com.aso.service.page;

import com.aso.model.dto.ProductDTO;
import com.aso.repository.page.PagingAndSortingQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PageServiceImpl implements PageService{

    @Autowired
    private PagingAndSortingQuery pagingAndSortingQuery;

    @Override
    public Page<ProductDTO> findAllProductDTOCreateAtASC(Pageable pageable) {
        return pagingAndSortingQuery.findAllProductDTOCreateAtASC(pageable);
    }

    @Override
    public Page<ProductDTO> findALl(String Categories, int choice, int option, String title, Pageable pageable) {
        return null;
    }

//    @Override
//    public Page<ProductDTO> findALl(String Categories, int choice, int option, String title, Pageable pageable) {
//        if (Categories.equals("%null%")) {
//            if (option == 1) {
//                if (choice == 1) {
//                    return pagingAndSortingQuery.findAllProductDTOCreateAtASC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, Categories, pageable);
//                }
//                if (choice == 2) {
//                    return pagingAndSortingQuery.findAllProductDTOCreateAtDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, Categories, pageable);
//                }
//                if (choice == 3) {
//                    return pagingAndSortingQuery.searchProductDTOByAvailableASC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, Categories,  pageable);
//                }
//                if (choice == 4) {
//                    return pagingAndSortingQuery.searchProductDTOByQueryActionBid(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, Categories, pageable);
//                }
//                if (choice == 5) {
//                    return pagingAndSortingQuery.searchProductDTOByTitleAndOtherQueryAvailableDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, Categories, pageable);
//                }
//                return pagingAndSortingQuery.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, Categories, pageable);
//            }
//        }
//        return null;
//    }
}
