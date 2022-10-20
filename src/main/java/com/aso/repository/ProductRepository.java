package com.aso.repository;


import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import com.aso.model.dto.ProductListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT MAX(id) FROM Product")
    Long findTopById();

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
            "FROM Product AS p WHERE p.deleted = false ORDER BY p.id DESC")
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
            " p.title LIKE %?1% ")
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
    Optional<ProductDTO> findProductDTOById(Long id);

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
            "FROM Product AS p WHERE p.deleted = false AND p.available >=0")
    List<ProductDTO> findAllProductDTOByAvailable(String available);

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
<<<<<<< HEAD
            "FROM Product AS p  WHERE p.title LIKE :keyword " +
=======
            "FROM Product AS p WHERE p.title LIKE :keyword " +
>>>>>>> development
            "OR p.category.title LIKE :keyword AND p.deleted= false ORDER BY p.id DESC " +
            "")
    Page<ProductDTO> findAllProductss(Pageable pageable, @Param("keyword") String keyword);

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
            "FROM Product AS p WHERE p.deleted = false ORDER BY p.id ASC")
    Page<ProductDTO> findAllProducts(Pageable pageable);
}
