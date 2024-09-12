package com.joboffers.infrastructure.usermanagement.controller.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String username, String token) {

}
