package com.joboffers;

import com.joboffers.infrastructure.offer.client.OfferClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({OfferClientProperties.class})
public class JobOffersApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JobOffersApplication.class, args);
    }
    
}
