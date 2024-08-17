package com.joboffers.infrastructure.offer.scheduler;

import com.joboffers.infrastructure.offer.client.OfferClient;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
class OfferScheduler {
    
    private final OfferClient offerClient;
    
    @Scheduled(cron = "${job-offers.offer.scheduler.offer-update-occurrence}")
    public void scheduledOfferUpdate() {
        log.info("Scheduling offer update job");
        int size = offerClient.fetchNewOffers().size();
        log.info("Found {} new offers", size);
    }
}
