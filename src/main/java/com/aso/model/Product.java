package com.aso.model;



import com.aso.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String slug;
    @NotNull
    private String image;
    @NotNull
    private Long sold = 0L;
    @NotNull
    private Long viewed = 0L;

    @NotNull
    private Boolean action;

    @NotNull
    private Long available;
    @NotNull
    @Column(precision = 12, scale = 0)
    private BigDecimal price = new BigDecimal ( 0L );

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @Column(columnDefinition = "boolean default false")
    private Boolean moderation = false;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;



    public ProductDTO toProductDTO() {
        return new ProductDTO ()
                .setId ( id )
                .setTitle ( title )
                .setSlug ( slug )
                .setImage ( image )
                .setSold ( sold )
                .setViewed ( viewed )
                .setPrice ( price )
                .setCategory ( category.toCategoryDTO () );
    }
}