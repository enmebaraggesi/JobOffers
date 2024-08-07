package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.error.DuplicateOfferUrlException;
import com.joboffers.domain.offer.error.OfferNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {
    
    private final OffersRepository repository;
    private final ExternalFetchable offerFetcher;
    
    public List<OfferDto> findAllOffers() {
        List<Offer> offers = repository.findAll();
        return OfferMapper.mapOfferListToOfferDtoList(offers);
    }
    
    OfferDto findOfferById(final Long id) {
        Offer offer = repository.findById(id)
                                .orElseThrow(() -> new OfferNotFoundException("No offer found with id " + id));
        return OfferMapper.mapOfferToOfferDto(offer);
    }
    
    OfferDto saveOffer(final OfferRequestDto requestDto) {
        if (OfferInspector.isDuplicateUrl(repository, requestDto)) {
            throw new DuplicateOfferUrlException("There is already offer with url " + requestDto.url());
        }
        Offer offer = OfferMapper.mapOfferRequestDtoToOffer(requestDto);
        Offer saved = repository.save(offer);
        return OfferMapper.mapOfferToOfferDto(saved);
    }
    
    void fetchNewOffers() {
        List<OfferRequestDto> fetchedOffers = offerFetcher.fetchNewOffers();
        List<OfferDto> allOffers = this.findAllOffers();
        List<OfferRequestDto> filteredRequestDtos = OfferInspector.filterOutExistingOffers(fetchedOffers, allOffers);
        filteredRequestDtos.forEach(this::saveOffer);
    }
}
