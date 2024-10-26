package com.joboffers.domain.offer.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record OfferResponseDto(String id,
                               String position,
                               String company,
                               String salary,
                               String url) implements Serializable {
    
}
