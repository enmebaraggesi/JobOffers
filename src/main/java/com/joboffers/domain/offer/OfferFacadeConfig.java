package com.joboffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
class OfferFacadeConfig {
    
    @Bean
    public OfferFacade getOfferFacade(ExternalFetchable fetcher) {
        OffersRepository repository = new OffersRepository() {
            @Override
            public List<Offer> findAll() {
                return List.of();
            }
            
            @Override
            public Optional<Offer> findById(final Long id) {
                return Optional.empty();
            }
            
            @Override
            public Offer save(final Offer offer) {
                return null;
            }
            
            @Override
            public Optional<Offer> findByUrl(final String url) {
                return Optional.empty();
            }
        };
        OfferInspector inspector = new OfferInspector(repository);
        return new OfferFacade(repository, fetcher, inspector);
    }
}
