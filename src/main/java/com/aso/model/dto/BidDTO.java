package com.aso.model.dto;

import com.aso.validators.PriceConstraint;
import lombok.*;

import javax.validation.constraints.Email;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BidDTO {

//    @Schema(example = "example@mail.com")
    @Email(regexp = "[^@]+@[^@]+\\.[^@.]+", message = "Email không hợp lệ!")
    private String email;

    @PriceConstraint
    private BigDecimal bidPrice;

}
