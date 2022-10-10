package com.aso.repository;

import com.aso.model.OrderDetail;
import com.aso.model.dto.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    @Query("SELECT NEW com.aso.model.dto.OrderDetailDTO (" +
            "od.id," +
            "od.order," +
            "od.product," +
            "od.price," +
            "od.quantity," +
            "od.amountTransaction," +
            "od.statusOrderDetail,  " +
            "od.createdAt ," +
            "od.updatedAt " +
            ") " +
            "FROM OrderDetail od WHERE od.id = ?1 ")
    List<OrderDetailDTO> findAllOrderDetailId(String id);

    @Query("SELECT NEW com.aso.model.dto.OrderDetailDTO (" +
            "o.id," +
            "o.order," +
            "o.product," +
            "o.price," +
            "o.quantity," +
            "o.amountTransaction," +
            "o.statusOrderDetail,  " +
            "o.createdAt ," +
            "o.updatedAt " +
            ")  " +
            "FROM OrderDetail o " +
            "WHERE FUNCTION('MONTH', o.createdAt) = :createMonth " +
            "AND FUNCTION('YEAR', o.createdAt) = :createYear " +
            "AND o.statusOrderDetail = :statusOrderDetail " +
             "")
    List<OrderDetailDTO> findOderByCreateMonthYearAndStatusOrderDetail(@Param("createMonth") int createMonth, @Param("createYear") int createYear, @Param("statusOrderDetail") String statusOrderDetail);
}