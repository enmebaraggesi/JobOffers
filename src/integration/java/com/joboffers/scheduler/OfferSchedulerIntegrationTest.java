package com.joboffers.scheduler;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.infrastructure.offer.client.OfferClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "job-offers.offer.scheduler.scheduling.enabled=true")
class OfferSchedulerIntegrationTest extends BaseIntegrationTest {
    
    @SpyBean
    OfferClient offerClient;
    
    @Container
    public static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:5.0"));
    
    @DynamicPropertySource
    public static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
    }
    
    @Test
    void should_run_offer_client_method_exact_number_of_times() {
        await().atMost(Duration.ofSeconds(2))
               .untilAsserted(() -> verify(offerClient, times(1)).fetchNewOffers());
    }
}
