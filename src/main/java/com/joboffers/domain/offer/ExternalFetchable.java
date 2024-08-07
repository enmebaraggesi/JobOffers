package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;

import java.util.List;

public interface ExternalFetchable {
    
    List<OfferRequestDto> fetchNewOffers();
}
