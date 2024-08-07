package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;

import java.util.Collections;
import java.util.List;

class OfferFacadeTestConfig {
    
    static OfferFacade createForTest() {
        OffersRepository repository = new OffersRepositoryTestImpl();
        ExternalFetchable externalFetcher = new ExternalFetchableTestImpl(Collections.emptyList());
        OfferInspector offerInspector = new OfferInspector(repository);
        return new OfferFacade(repository, externalFetcher, offerInspector);
    }
    
    static OfferFacade createForTestWithExternalThreeOffers() {
        OffersRepository repository = new OffersRepositoryTestImpl();
        ExternalFetchable externalFetcher = new ExternalFetchableTestImpl(prepareExternalOffers());
        OfferInspector offerInspector = new OfferInspector(repository);
        return new OfferFacade(repository, externalFetcher, offerInspector);
    }
    
    private static List<OfferRequestDto> prepareExternalOffers() {
        return List.of(
                OfferRequestDto.builder()
                               .position("testPosition1")
                               .company("testCompany1")
                               .salary("1")
                               .url("https://example1.com")
                               .build(),
                OfferRequestDto.builder()
                               .position("testPosition2")
                               .company("testCompany2")
                               .salary("2")
                               .url("https://example2.com")
                               .build(),
                OfferRequestDto.builder()
                               .position("testPosition3")
                               .company("testCompany3")
                               .salary("3")
                               .url("https://example3.com")
                               .build()
        );
    }
}
