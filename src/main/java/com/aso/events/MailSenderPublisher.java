package com.aso.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MailSenderPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishNewBid(List<String> emails, long offerId, BigDecimal bidPrice) {
        applicationEventPublisher.publishEvent(new MailNewBidEvent(emails, offerId, bidPrice));
    }
    public void publishDeletedBid(List<String> emails, long offerId, BigDecimal bidPrice) {
        applicationEventPublisher.publishEvent(new MailDeletedBidEvent(emails, offerId, bidPrice));
    }
}
