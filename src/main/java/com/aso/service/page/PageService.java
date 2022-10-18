package com.aso.service.page;

import com.aso.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageService {
    Page<ProductDTO> findAllProductDTOCreateAtASC(Pageable pageable);

    Page<ProductDTO> findALl(String Categories,int choice, int option , String title, Pageable pageable);
}
