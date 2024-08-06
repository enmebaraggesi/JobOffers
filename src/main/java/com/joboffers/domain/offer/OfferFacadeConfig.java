package com.joboffers.domain.offer;

class OfferFacadeConfig {
    
    static OfferFacade createForTest(OffersRepository repository) {
        return new OfferFacade(repository);
    }
}
