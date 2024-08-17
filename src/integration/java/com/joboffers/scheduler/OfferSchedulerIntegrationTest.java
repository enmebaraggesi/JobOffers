package com.joboffers.scheduler;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.domain.offer.OfferFacade;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "job-offers.offer.scheduler.scheduling.enabled=true")
class OfferSchedulerIntegrationTest extends BaseIntegrationTest {
    
    @SpyBean
    OfferFacade offerFacade;
    
    @Test
    void should_run_offer_client_method_exact_number_of_times() {
        await().atMost(Duration.ofSeconds(2))
               .untilAsserted(() -> verify(offerFacade, times(2)).findAllOffers());
    }
}
