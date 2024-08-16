package com.joboffers.infrastructure.offer.client.dto;

import lombok.Builder;

@Builder
public record OfferExternalResponseDto(String title,
                                       String company,
                                       String salary,
                                       String offerUrl) {
    
}
