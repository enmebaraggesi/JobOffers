package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ExternalFetchableTestImpl implements ExternalFetchable {
    
    Map<String, OfferRequestDto> inMemoryExternalDatabase = new ConcurrentHashMap<>();
    
    ExternalFetchableTestImpl(List<OfferRequestDto> offers) {
        offers.forEach(offer -> inMemoryExternalDatabase.put(offer.url(), offer));
    }
    
    @Override
    public List<OfferRequestDto> fetchNewOffers() {
        return inMemoryExternalDatabase.values()
                                       .stream()
                                       .toList();
    }
}
