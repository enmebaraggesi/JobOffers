package com.joboffers.domain.offer;

class OfferFacadeConfig {
    
    static OfferFacade createForTest(OffersRepository repository, ExternalFetchable externalFetcher) {
        return new OfferFacade(repository, externalFetcher);
    }
}
