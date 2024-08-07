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
        String url = requestDto.url();
        if (repository.findByUrl(url)) {
            throw new DuplicateOfferUrlException("There is already offer with url " + url);
        }
        Offer offer = OfferMapper.mapOfferRequestDtoToOffer(requestDto);
        Offer saved = repository.save(offer);
        return OfferMapper.mapOfferToOfferDto(saved);
    }
}
