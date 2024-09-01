package com.joboffers.domain.offer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OfferRequestDto(
        @NotEmpty(message = "position must not be empty")
        @NotNull(message = "position must not be null")
        String position,
        @NotEmpty(message = "company must not be empty")
        @NotNull(message = "company must not be null")
        String company,
        @NotEmpty(message = "salary must not be empty")
        @NotNull(message = "salary must not be null")
        String salary,
        @NotEmpty(message = "url must not be empty")
        @NotNull(message = "url must not be null")
        String url) {
    
}
