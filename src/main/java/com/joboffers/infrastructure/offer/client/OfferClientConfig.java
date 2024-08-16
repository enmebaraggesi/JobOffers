package com.joboffers.infrastructure.offer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferClientConfig {
    
    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder().errorHandler(restTemplateResponseErrorHandler)
                                        .setConnectTimeout(Duration.ofMillis(1000))
                                        .setReadTimeout(Duration.ofMillis(1000))
                                        .build();
    }
    
    @Bean
    public OfferClient offerClient(RestTemplate restTemplate,
                                   @Value("${job-offers.offer.http.client.url}") String url,
                                   @Value("${job-offers.offer.http.client.port}") int port) {
        return new OfferClient(restTemplate, url, port);
    }
}
