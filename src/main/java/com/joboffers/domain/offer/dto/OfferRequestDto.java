package com.joboffers.domain.offer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OfferRequestDto(
        @NotEmpty(message = "{position.not.empty}")
        @NotNull(message = "{position.not.null}")
        String position,
        @NotEmpty(message = "{company.not.empty}")
        @NotNull(message = "{company.not.null}")
        String company,
        @NotEmpty(message = "{salary.not.empty}")
        @NotNull(message = "{salary.not.null}")
        String salary,
        @NotEmpty(message = "{url.not.empty}")
        @NotNull(message = "{url.not.null}")
        String url) {
    
}
