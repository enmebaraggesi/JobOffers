package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

class OfferInspector {
    
    List<OfferRequestDto> filterOutExistingOffers(final List<OfferRequestDto> fetched, final List<OfferResponseDto> existing) {
        return fetched.stream()
                      .filter(offer -> isNoneMatch(existing, offer))
                      .toList();
    }
    
    private boolean isNoneMatch(List<OfferResponseDto> existing, OfferRequestDto offer) {
        return existing.stream()
                       .noneMatch(offerDto -> offerDto.url().equals(offer.url()));
    }
}
