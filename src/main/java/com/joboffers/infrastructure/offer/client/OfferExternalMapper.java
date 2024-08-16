package com.joboffers.infrastructure.offer.client;

import com.joboffers.domain.offer.dto.OfferRequestDto;
import com.joboffers.infrastructure.offer.client.dto.OfferExternalResponseDto;

import java.util.List;

class OfferExternalMapper {
    
    static List<OfferRequestDto> mapOfferExternalResponseDtoListToOfferRequestDtoList(final List<OfferExternalResponseDto> list) {
        return list.stream()
                   .map(OfferExternalMapper::mapOfferExternalResponseDtoToOfferRequestDto)
                   .toList();
    }
    
    private static OfferRequestDto mapOfferExternalResponseDtoToOfferRequestDto(OfferExternalResponseDto dto) {
        return OfferRequestDto.builder()
                              .position(dto.position())
                              .company(dto.company())
                              .salary(dto.salary())
                              .url(dto.url())
                              .build();
    }
}
