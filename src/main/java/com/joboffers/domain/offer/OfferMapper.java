package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;

import java.util.List;

class OfferMapper {
    
    static List<OfferDto> mapOfferListToOfferDtoList(final List<Offer> offers) {
        return offers.stream()
                     .map(offer -> OfferDto.builder()
                                           .id(offer.id())
                                           .position(offer.position())
                                           .company(offer.company())
                                           .salary(offer.salary())
                                           .url(offer.url())
                                           .build())
                     .toList();
    }
    
    static OfferDto mapOfferToOfferDto(final Offer offer) {
        return OfferDto.builder()
                       .id(offer.id())
                       .position(offer.position())
                       .company(offer.company())
                       .salary(offer.salary())
                       .url(offer.url())
                       .build();
    }
    
    static Offer mapOfferDtoToOffer(final OfferDto offerDto) {
        return Offer.builder()
                    .id(offerDto.id())
                    .position(offerDto.position())
                    .company(offerDto.company())
                    .salary(offerDto.salary())
                    .url(offerDto.url())
                    .build();
    }
}
