package com.joboffers.infrastructure.offer.scheduler;

import com.joboffers.domain.offer.OfferFacade;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
@AllArgsConstructor
public class OfferScheduler {
    
    private final OfferFacade offerFacade;
    private static final String STARTED_FETCHING_MESSAGE = "Started fetching offers {}";
    private static final String FINISHED_FETCHING_MESSAGE = "Finished fetching offers {}";
    private static final String NEW_OFFERS_ADDED_MESSAGE = "Added {} new offers";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    
    @Scheduled(fixedDelayString = "${job-offers.offer.scheduler.offer-update-occurrence}")
    public void scheduledOfferUpdate() {
        log.info(STARTED_FETCHING_MESSAGE, DATE_FORMAT.format(new Date()));
        offerFacade.fetchNewOffers();
        int size = offerFacade.findAllOffers().size();
        log.info(NEW_OFFERS_ADDED_MESSAGE, size);
        log.info(FINISHED_FETCHING_MESSAGE, DATE_FORMAT.format(new Date()));
    }
}
