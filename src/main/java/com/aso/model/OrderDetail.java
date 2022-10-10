package com.aso.model;


import com.aso.model.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_items")
@Accessors(chain = true)
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal amountTransaction;

    private String statusOrderDetail;

    public OrderDetailDTO toOrderDetailDTO() {
    return new OrderDetailDTO()
            .setId(id)
            .setOrder(order)
            .setProduct(product)
            .setPrice(price)
            .setQuantity(quantity)
            .setAmountTransaction(amountTransaction)
            .setStatusOrderDetail(statusOrderDetail)
            ;
    }
}
