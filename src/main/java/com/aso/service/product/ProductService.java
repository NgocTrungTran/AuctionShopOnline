package com.aso.service.product;


import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import com.aso.model.dto.ProductListDTO;
import com.aso.service.IGeneralService;
<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
>>>>>>> development

import java.util.List;
import java.util.Optional;

public interface ProductService extends IGeneralService<Product> {

    List<ProductListDTO> findAllProductListDTO();

    List<ProductDTO> findAllProductsDTO();

    List<ProductDTO> findAllProductsDTOTrash();

    Boolean existsByTitle(String title);

    void softDelete(Product product);
    List<ProductDTO> findAllBySearchTitle(String title);

    List<ProductDTO> findAllBySearchSlug(String slug);

    Optional<ProductDTO> findProductDTOBySlug (String slug);

    Optional<Product> findProductBySlug(String slug);

<<<<<<< HEAD
=======
    List<ProductDTO> findAllProductDTOByAvailable(String available);

>>>>>>> development
}
