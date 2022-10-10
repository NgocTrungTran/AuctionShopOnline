package com.aso.repository;


import com.aso.model.Product;
import com.aso.model.dto.IProductDTO;
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
            "p.category, " +
            "p.description " +
            ") " +
            "FROM Product AS p WHERE p.deleted = false")
    List<ProductDTO> findAllProductsDTO();

//    @Query("SELECT NEW com.aso.model.dto.ProductDTO (" +
//            "p.id, " +
//            "p.title, " +
//            "p.slug, " +
//            "p.image, " +
//            "p.price, " +
//            "p.sold, " +
//            "p.viewed, " +
//            "p.category, " +
//            "p.available " +
//            ") " +
//            "FROM Product AS p WHERE p.deleted = true ")
//    List<ProductDTO> findAllProductsDTOTrash();

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
            "p.available, " +
            "p.description, " +
            "p.action " +
            ") " +
            "FROM Product AS p WHERE  " +
            " p.title like %?1% ")
    List<ProductDTO> findAllBySearchTitle(String title);

    @Query("SELECT NEW com.aso.model.dto.ProductDTO ( " +
            "p.id, " +
            "p.title, " +
            "p.slug, " +
            "p.image, " +
            "p.price, " +
            "p.sold, " +
            "p.viewed, " +
            "p.category, " +
            "p.available, " +
            "p.description, " +
            "p.action " +
            ") " +
            "FROM Product AS p WHERE  " +
            " p.slug like %?1% ")
    Optional<ProductDTO> findProductDTOBySlug(String slug);

    @Query("SELECT NEW com.aso.model.dto.ProductDTO ( " +
            "p.id, " +
            "p.title, " +
            "p.slug, " +
            "p.image, " +
            "p.price, " +
            "p.sold, " +
            "p.viewed, " +
            "p.category, " +
            "p.available, " +
            "p.description, " +
            "p.action " +
            ") " +
            "FROM Product AS p WHERE  " +
            " p.slug like %?1% ")
    List<ProductDTO> findAllBySearchSlug(String slug);

    //    @Query("SELECT " +
//            "pm.product.id AS id, " +
//            "pm.product.title AS productTitle, " +
//            "pm.product.slug AS productSlug, " +
//            "pm.product.image AS productImage, " +
//            "pm.product.price AS productPrice, " +
//            "pm.product.sold AS productSold, " +
//            "pm.product.viewed AS productViewed, " +
//            "pm.product.category AS productCategory, " +
//            "pm.product.available AS productAvailable, " +
//            "pm.product.description AS productDescription, " +
//            "pm.id AS fileId, " +
//            "pm.fileName AS fileName, " +
//            "pm.fileFolder AS fileFolder, " +
//            "pm.fileUrl AS fileUrl, " +
//            "pm.fileType AS fileType " +
//            "FROM ProductMedia pm " +
//            "WHERE pm.product.title LIKE %?1%"
//    )
//    Iterable<IProductDTO> findProductByProductName(String search);
//    @Query("SELECT " +
//            "pm.product.id AS id, " +
//            "pm.product.title AS productTitle, " +
//            "pm.product.slug AS productSlug, " +
//            "pm.product.image AS productImage, " +
//            "pm.product.price AS productPrice, " +
//            "pm.product.sold AS productSold, " +
//            "pm.product.viewed AS productViewed, " +
//            "pm.product.category AS productCategory, " +
//            "pm.product.available AS productAvailable, " +
//            "pm.product.description AS productDescription, " +
//            "pm.id AS fileId, " +
//            "pm.fileName AS fileName, " +
//            "pm.fileFolder AS fileFolder, " +
//            "pm.fileUrl AS fileUrl, " +
//            "pm.fileType AS fileType " +
//            "FROM ProductMedia pm " +
//            "ORDER BY pm.product.title ASC"
//    )
//    Iterable<IProductDTO> findAllIProductDTO();
    @Query("SELECT NEW com.aso.model.dto.ProductDTO ( " +
            "p.id, " +
            "p.title, " +
            "p.slug, " +
            "p.image, " +
            "p.price, " +
            "p.sold, " +
            "p.viewed, " +
            "p.category, " +
            "p.available, " +
            "p.description, " +
            "p.action " +
            ") " +
            "FROM Product AS p WHERE  " +
            " p.slug like %?1% ")
    Optional<Product> findProductBySlug(String slug);

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
            "p.description, " +
            "p.action " +
            ") " +
            "FROM Product p WHERE " +
            "p.id = ?1 " +
            "And p.deleted = false")
    Optional<ProductDTO> findProductDTOById(String id);
}
