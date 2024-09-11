package com.joboffers.domain.usersmanagement.dto;

import lombok.Builder;

@Builder
public record UserRegistrationResponseDto(String id,
                                          String name,
                                          boolean created) {
    
}
