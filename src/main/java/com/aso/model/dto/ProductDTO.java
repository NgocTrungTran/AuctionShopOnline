package com.aso.model.dto;

import com.aso.model.Category;
import com.aso.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDTO implements Validator {
    private Long id;

    private String title;
    private String slug;
    private String image;
    private Long sold;
    private Long viewed;

    private Boolean action;
    @Max(value = 1000)
    @Min(value = 0)
    private Long available;
    private BigDecimal price;

    private CategoryDTO category;

    private Boolean moderation;

    private String createdBy;

    public ProductDTO(Long id, String title, String slug, String image, BigDecimal price, Long sold, Long viewed, Category category, Long available) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.image = image;
        this.price = price;
        this.sold = sold;
        this.viewed = viewed;
        this.category = category.toCategoryDTO ();
        this.available = available;
    }

    public Product toProduct() {
        return new Product ()
                .setId ( id )
                .setTitle ( title )
                .setSlug ( slug )
                .setPrice ( price )
                .setImage ( image )
                .setCategory ( category.toCategory () )
                ;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.isAssignableFrom ( clazz );
    }


    @Override
    public void validate(Object o, Errors errors) {
        ProductDTO productDTO = (ProductDTO) o;
        String price = productDTO.getPrice ().toString ();


        if ( !com.aso.utils.Validation.isNumberValid ( price ) ) {

            if ( price == null || price.equals ( "" ) ) {
                errors.rejectValue ( "price", "400", "Price not null!" );
            } else {
                errors.rejectValue ( "price", "400", "Price invalid!" );
            }

        } else {
            if ( price.length () > 9 ) {
                errors.rejectValue ( "price", "400", "Max price is 100.000.000đ!" );
            } else {

                long validPrice = Long.parseLong ( price );
                if ( validPrice < 99999 ) {
                    errors.rejectValue ( "price", "400", "Min price is 100.000đ!" );
                }

                if ( validPrice > 100000000 ) {
                    errors.rejectValue ( "price", "400", "Max price is 100.000.000đ!" );
                }
            }
        }
    }
}
