package com.aso.repository;


import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import com.aso.model.dto.ProductListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT NEW com.aso.model.dto.ProductListDTO (" +
                "p.id, " +
                "p.title, " +
                "p.slug, " +
                "p.image, " +
                "p.price " +
            ") " +
            "FROM Product AS p")
    List<ProductListDTO> findAllProductListDTO();

    @Query("SELECT NEW com.aso.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.slug, " +
            "p.image, " +
            "p.price, " +
            "p.sold, " +
            "p.viewed, " +
            "p.category, " +
            "p.available " +
            ") " +
            "FROM Product AS p WHERE p.deleted = false")
    List<ProductDTO> findAllProductsDTO();

    @Query("SELECT NEW com.aso.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.slug, " +
            "p.image, " +
            "p.price, " +
            "p.sold, " +
            "p.viewed, " +
            "p.category, " +
            "p.available " +
            ") " +
            "FROM Product AS p WHERE p.deleted = true ")
    List<ProductDTO> findAllProductsDTOTrash();

    Boolean existsByTitle(String title);

    void deleteById(Product id);

    @Query("SELECT NEW com.aso.model.dto.ProductDTO ( " +
            "p.id, " +
            "p.title, " +
            "p.slug, " +
            "p.image, " +
            "p.price, " +
            "p.sold, " +
            "p.viewed, " +
            "p.category, " +
            "p.available) " +
            "FROM Product  p WHERE  " +
            " p.title like %?1% ")
    List<ProductDTO> findProductValue(String query);

//    @Query("SELECT NEW com.aso.model.dto.ProductDTO (" +
//            "c.id, " +
//            "c.title, " +
//            "c.slug, " +
//            "c.image, " +
//            "c.sold," +
//            "c.viewed," +
//            "c.action," +
//            "c.available," +
//            "c.price, " +
//            "c.category " +
//            ")  " +
//            "FROM Product c WHERE " +
//            "c.sold = ?1 " +
//            "And c.deleted = false")
//    Optional<ProductDTO> findProductDTOBySlug (String slug);
}
