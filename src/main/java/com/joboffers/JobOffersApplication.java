package com.joboffers;

import com.joboffers.infrastructure.offer.client.OfferClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({OfferClientProperties.class})
public class JobOffersApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
    
}
