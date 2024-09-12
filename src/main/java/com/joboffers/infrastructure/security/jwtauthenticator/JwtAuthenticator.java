package com.joboffers.infrastructure.security.jwtauthenticator;

import com.joboffers.infrastructure.security.jwtauthenticator.dto.JwtResponseDto;
import com.joboffers.infrastructure.security.token.dto.TokenRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtAuthenticator {
    
    private final AuthenticationManager authenticationManager;
    
    public JwtResponseDto authenticateAndGenerateToken(final TokenRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password())
        );
        return JwtResponseDto.builder().build();
    }
}
