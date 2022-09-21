package com.aso.model;


import com.aso.model.dto.CartItemListDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items")
@Accessors(chain = true)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private String title;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

//    @OneToMany(mappedBy = "cartItem")
//    private List<CartItem> cartItem;

    public CartItemListDTO toCartItemListDTO() {
        return new CartItemListDTO()
                .setId(id)
                .setProductId (product.toProductDTO ())
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setAmount(amount);
    }
}