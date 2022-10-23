package com.aso.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MailNewBidEvent {

    private List<String> emails;
    private long offerId;
    private BigDecimal bidPrice;

}
