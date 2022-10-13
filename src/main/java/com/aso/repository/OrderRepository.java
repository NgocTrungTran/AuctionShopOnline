package com.aso.repository;


import com.aso.model.Order;
import com.aso.model.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT NEW com.aso.model.dto.OrderDTO(" +
            "o.id, " +
            "o.locationRegion, " +
            "o.description, " +
            "o.statusOrder, " +
            "o.orderDetail, " +
            "o.createdAt, " +
            "o.updatedAt " +
            ") " +
            "FROM Order AS o  WHERE o.id = ?1 ")
    List<OrderDTO> findOrderDTOById(String id);

    @Query("SELECT NEW com.aso.model.dto.OrderDTO(" +
            "o.id, " +
            "o.locationRegion, " +
            "o.description, " +
            "o.statusOrder, " +
            "o.orderDetail, " +
            "o.createdAt, " +
            "o.updatedAt " +
            ") " +
            "FROM Order AS o ")
    List<OrderDTO> findOrderDTO();

    @Query("SELECT NEW com.aso.model.dto.OrderDTO(" +
            "o.id, " +
            "o.locationRegion, " +
            "o.description, " +
            "o.statusOrder, " +
            "o.orderDetail, " +
            "o.createdAt, " +
            "o.updatedAt " +
            ") " +
            "FROM Order o where o.statusOrder = ?1")
    List<OrderDTO> findOrderDTOByDeliver(String order);

    @Query("SELECT NEW com.aso.model.dto.OrderDTO(" +
            "o.id, " +
            "o.locationRegion, " +
            "o.description, " +
            "o.statusOrder, " +
            "o.orderDetail, " +
            "o.createdAt, " +
            "o.updatedAt " +
            ") " +
            "FROM Order o  WHERE  o.orderDetail.id = ?1  ")
    List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id);
}
