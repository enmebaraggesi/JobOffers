package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
class OfferInspector {
    
    private final OffersRepository repository;
    private String error;
    
    List<OfferRequestDto> filterOutExistingOffers(final List<OfferRequestDto> fetched, final List<OfferResponseDto> existing) {
        return fetched.stream()
                      .filter(offer -> isNoneMatch(existing, offer))
                      .toList();
    }
    
    private boolean isNoneMatch(List<OfferResponseDto> existing, OfferRequestDto offer) {
        return existing.stream()
                       .noneMatch(offerDto -> offerDto.url().equals(offer.url()));
    }
    
    boolean inspectOfferRequest(final Offer offer) {
        return isUrlValid(offer.url());
    }
    
    private boolean isUrlValid(final String url) {
        if (repository.existsByUrl(url)) {
            error = "Offer URL: " + url + " already exists";
            return false;
        }
        return true;
    }
}
