package com.aso.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class HistoryDepositDTO {
    private Long id;
    private String createdBy;
    private String fullName;
    private BigDecimal transactionAmount;
    private Date createdAt;
}
