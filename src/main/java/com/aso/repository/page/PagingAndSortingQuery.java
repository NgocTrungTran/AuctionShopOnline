package com.aso.repository.page;

import com.aso.model.Product;
import com.aso.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PagingAndSortingQuery extends PagingAndSortingRepository<Product, Long> {

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
            "FROM Product p WHERE p.deleted = false ORDER BY p.createdAt ASC")
    Page<ProductDTO> findAllProductDTOCreateAtASC(Pageable pageable);

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
            "FROM Product p WHERE p.deleted = false ORDER BY p.createdAt DESC")
    Page<ProductDTO> findAllProductDTOCreateAtDESC(Pageable pageable);

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
            "FROM Product p WHERE " +
            "p.deleted = false " +
            "ORDER BY p.price ASC" +
            " ")
    Page<ProductDTO> searchProductDTOByPriceASC(BigDecimal price, Pageable pageable);

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
            "FROM Product p WHERE " +
            "p.deleted = false " +
            "ORDER BY p.price DESC" +
            " ")
    Page<ProductDTO> searchProductDTOByPriceDESC(BigDecimal price, Pageable pageable);

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
            "FROM Product p WHERE " +
            "p.deleted = false " +
            "ORDER BY p.available ASC" +
            " ")
    Page<ProductDTO> searchProductDTOByAvailableASC(Long available, Pageable pageable);

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
            "FROM Product p WHERE " +
            "p.deleted = false " +
            "ORDER BY p.available DESC" +
            " ")
    Page<ProductDTO> searchProductDTOByAvailableDESC(Long available, Pageable pageable);

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
            "FROM Product p JOIN Category c WHERE p.title LIKE ?3 AND p.category.title LIKE ?4 AND p.deleted = false ORDER BY p.price ASC" +
            " ")
    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal price, String title,String Categories, Pageable pageable);

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
            "FROM Product p JOIN Category c WHERE p.title LIKE ?3 AND p.category.title LIKE ?4 AND p.deleted = false ORDER BY p.price DESC" +
            " ")
    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal price, String title,String Categories, Pageable pageable);

    //    select * from products p JOIN categories c
//    where c.title like "a"
//    AND p.title like "tien"
//    AND p.deleted = false order by p.price DESC;

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
            "FROM Product p JOIN Category c WHERE p.deleted = false AND p.title LIKE ?3 AND p.category.title LIKE ?4 ORDER BY p.available ASC" +
            " ")
    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryAvailableASC(Long available, String title,String Categories, Pageable pageable);

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
            "FROM Product p JOIN Category c WHERE p.deleted = false AND p.title LIKE ?3 AND p.category.title LIKE ?4 ORDER BY p.available DESC" +
            " ")
    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryAvailableDESC(Long available, String title,String Categories, Pageable pageable);

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
            "FROM Product p WHERE p.deleted = false AND p.action = false" +
            " ")
    Page<ProductDTO> searchProductDTOByQueryActionBid(Pageable pageable);

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
            "FROM Product p WHERE p.deleted = false AND p.action = true" +
            " ")
    Page<ProductDTO> searchProductDTOByQueryActionBidTrue(Pageable pageable);
}
