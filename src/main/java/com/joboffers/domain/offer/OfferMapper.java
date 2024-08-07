package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferDto;
import com.joboffers.domain.offer.dto.OfferRequestDto;

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
    
    static Offer mapOfferRequestDtoToOffer(final OfferRequestDto requestDto) {
        return Offer.builder()
                    .position(requestDto.position())
                    .company(requestDto.company())
                    .salary(requestDto.salary())
                    .url(requestDto.url())
                    .build();
    }
}
