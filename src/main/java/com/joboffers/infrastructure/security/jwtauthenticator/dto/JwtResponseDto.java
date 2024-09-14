package com.joboffers.infrastructure.security.jwtauthenticator.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String username, String token) {

}
