package com.joboffers.http.error;

import com.joboffers.infrastructure.offer.client.OfferClient;
import com.joboffers.infrastructure.offer.client.OfferClientConfig;
import com.joboffers.infrastructure.offer.client.OfferClientProperties;
import com.joboffers.infrastructure.offer.client.RestTemplateResponseErrorHandler;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

@Setter
class OfferClientTestConfig extends OfferClientConfig {

    private OfferClientProperties properties;
    
    OfferClientTestConfig(final OfferClientProperties properties) {
        this.properties = properties;
    }
    
    OfferClient testOfferClient() {
        RestTemplateResponseErrorHandler errorHandler = restTemplateResponseErrorHandler();
        RestTemplate restTemplate = restTemplate(errorHandler, properties);
        return new OfferClient(restTemplate, properties);
    }
}
