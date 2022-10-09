package com.aso.repository;


import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import com.aso.model.dto.ProductListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            "p.createdAt, " +
            "p.createdBy, " +
            "p.updatedAt, " +
            "p.updatedBy, " +
            "p.action, " +
            "p.available, " +
            "p.image, " +
            "p.moderation, " +
            "p.price, " +
            "p.slug, " +
            "p.sold, " +
            "p.title, " +
            "p.viewed, " +
            "p.category " +
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
            "p.available, " +
            "p.createdBy" +
            ") " +
            "FROM Product AS p WHERE p.deleted = true ")
    List<ProductDTO> findAllProductsDTOTrash();

    Boolean existsByTitle(String title);
}
