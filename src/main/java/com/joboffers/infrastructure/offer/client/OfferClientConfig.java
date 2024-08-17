package com.joboffers.infrastructure.offer.client;

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
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler errorHandler,
                                     OfferClientProperties properties) {
        return new RestTemplateBuilder().errorHandler(errorHandler)
                                        .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                                        .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                                        .build();
    }
    
    @Bean
    public OfferClient offerClient(RestTemplate restTemplate, OfferClientProperties properties) {
        return new OfferClient(restTemplate, properties);
    }
}
