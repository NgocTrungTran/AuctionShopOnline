package com.aso.model.dto;

import com.aso.model.Order;
import com.aso.model.OrderDetail;
import com.aso.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailDTO {
    private Long id;
    private Order order;
    private Product product;
    private BigDecimal price;
    private int quantity;
    private BigDecimal amountTransaction;

    private String statusOrderDetail;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    public OrderDetail toOrderDetail(){
        return new OrderDetail()
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
