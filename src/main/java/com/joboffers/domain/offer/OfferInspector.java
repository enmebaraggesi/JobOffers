package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.error.DuplicateOfferUrlException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.joboffers.domain.offer.ResponseMessage.DUPLICATE_URL;

@AllArgsConstructor
class OfferInspector {
    
    private final OffersRepository repository;
    
    void inspectUrl(final OfferRequestDto dto) {
        String url = dto.url();
        if (isDuplicateUrl(url)) {
            throw new DuplicateOfferUrlException(DUPLICATE_URL.message);
        }
    }
    
    private boolean isDuplicateUrl(String url) {
        Optional<Offer> foundByUrl = repository.findByUrl(url);
        return foundByUrl.isPresent();
    }
    
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
