package com.joboffers.domain.offer.dto;

import lombok.Builder;

@Builder
public record OfferDto(Long id,
                       String position,
                       String company,
                       String salary,
                       String url) {
    
}
