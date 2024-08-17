package com.joboffers.infrastructure.offer.scheduler;

import com.joboffers.infrastructure.offer.client.OfferClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log4j2
@Getter
@AllArgsConstructor
public class OfferScheduler {
    
    private final OfferClient offerClient;
    private final AtomicInteger counter = new AtomicInteger(0);
    
    @Scheduled(cron = "${job-offers.offer.scheduler.offer-update-occurrence}")
    public void scheduledOfferUpdate() {
        log.info("Scheduling offer update job");
        int size = offerClient.fetchNewOffers().size();
        log.info("Found {} new offers", size);
        counter.getAndIncrement();
    }
}
