package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import com.joboffers.domain.offer.dto.OfferRequestDto;

import java.util.List;
import java.util.Optional;

class OfferInspector {
    
    static boolean isDuplicateUrl(final OffersRepository repository, final OfferRequestDto dto) {
        String url = dto.url();
        Optional<Offer> foundByUrl = repository.findByUrl(url);
        return foundByUrl.isPresent();
    }
    
    static List<OfferRequestDto> filterOutExistingOffers(final List<OfferRequestDto> fetched, final List<OfferDto> existing) {
        return fetched.stream()
                      .filter(offer -> existing.stream()
                                               .noneMatch(offerDto -> offerDto.url()
                                                                              .equals(offer.url())))
                      .toList();
    }
}
