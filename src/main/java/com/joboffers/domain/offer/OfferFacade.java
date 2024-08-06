package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OfferFacade {
    
    private final OffersRepository repository;
    
    public List<OfferDto> findAllOffers() {
        List<Offer> offers = repository.findAll();
        return OfferMapper.mapOfferListToOfferDtoList(offers);
    }
}
