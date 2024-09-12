package com.joboffers.infrastructure.security.jwtauthenticator;

import com.joboffers.infrastructure.usermanagement.controller.dto.JwtResponseDto;
import com.joboffers.infrastructure.usermanagement.controller.dto.TokenRequestDto;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticator {
    
    public JwtResponseDto authenticateAndGenerateToken(final TokenRequestDto requestDto) {
        return null;
    }
}
