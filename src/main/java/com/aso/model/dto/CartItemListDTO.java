package com.aso.model.dto;

import com.aso.model.Cart;
import com.aso.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartItemListDTO {

    private Long id;
    private ProductDTO productId;

    private String title;

    private BigDecimal price;

    private int quantity;

    private BigDecimal amountTransaction;

    private Cart cart;

    public CartItemListDTO(Long id, Product productId, String title, BigDecimal price, int quantity, BigDecimal amountTransaction) {
        this.id = id;
        this.productId = productId.toProductDTO ();
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.amountTransaction = amountTransaction;
    }
}
