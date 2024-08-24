package com.joboffers.domain.usersmanagement.dto;

import lombok.Builder;

@Builder
public record UserResponseDto(String id,
                              String name,
                              String email) {
    
}
