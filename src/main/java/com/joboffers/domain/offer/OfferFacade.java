package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import com.joboffers.domain.offer.error.OfferNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {
    
    private final OffersRepository repository;
    
    public List<OfferDto> findAllOffers() {
        List<Offer> offers = repository.findAll();
        return OfferMapper.mapOfferListToOfferDtoList(offers);
    }
    
    OfferDto findOfferById(final Long id) {
        Offer offer = repository.findById(id)
                                .orElseThrow(() -> new OfferNotFoundException("No offer found with id " + id));
        return OfferMapper.mapOfferToOfferDto(offer);
    }
    
    OfferDto saveOffer(final OfferDto offerDto) {
        Offer offer = OfferMapper.mapOfferDtoToOffer(offerDto);
        Offer saved = repository.save(offer);
        return OfferMapper.mapOfferToOfferDto(saved);
    }
}
