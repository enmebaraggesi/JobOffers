package com.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OfferFacadeConfig {
    
    @Bean
    public OfferFacade getOfferFacade(ExternalFetchable fetcher, OffersRepository repository) {
        OfferInspector inspector = new OfferInspector(repository);
        return new OfferFacade(repository, fetcher, inspector);
    }
}
