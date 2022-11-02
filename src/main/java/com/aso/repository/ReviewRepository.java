package com.aso.repository;

import com.aso.model.Review;
import com.aso.model.dto.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT NEW com.aso.model.dto.ReviewDTO (" +
            "c.id, " +
            "c.account, " +
            "c.product, " +
            "c.review, " +
            "c.vote, " +
            "c.createdAt, " +
            "c.createdBy, " +
            "c.deleted)  " +
            "FROM Review c  WHERE c.deleted = false ")
    List<ReviewDTO> findAllReviewsDTO();
}
