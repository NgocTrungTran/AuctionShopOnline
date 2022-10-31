package com.aso.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Chart {
    private Long id;
    private BigDecimal auction;
    private BigDecimal buy;
}