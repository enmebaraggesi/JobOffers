package com.joboffers.domain.usersmanagement.dto;

import lombok.Builder;

@Builder
public record UserRequestDto(String name,
                             String email,
                             String password) {
    
}
