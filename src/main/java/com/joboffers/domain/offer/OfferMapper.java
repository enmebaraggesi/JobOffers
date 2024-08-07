package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;

import java.util.List;

class OfferMapper {
    
    static List<OfferResponseDto> mapOfferListToOfferResponseDtoList(final List<Offer> offers) {
        return offers.stream()
                     .map(offer -> OfferResponseDto.builder()
                                                   .id(offer.id())
                                                   .position(offer.position())
                                                   .company(offer.company())
                                                   .salary(offer.salary())
                                                   .url(offer.url())
                                                   .build())
                     .toList();
    }
    
    static OfferResponseDto mapOfferToOfferResponseDto(final Offer offer) {
        return OfferResponseDto.builder()
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
