package com.joboffers.infrastructure.usermanagement.controller;

import com.joboffers.infrastructure.security.jwtauthenticator.JwtAuthenticator;
import com.joboffers.infrastructure.usermanagement.controller.dto.JwtResponseDto;
import com.joboffers.infrastructure.usermanagement.controller.dto.TokenRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
@AllArgsConstructor
class TokenRestController {

    private final JwtAuthenticator jwtAuthenticator;
    
    @PostMapping
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@RequestBody @Valid TokenRequestDto requestDto) {
        final JwtResponseDto jwtResponse = jwtAuthenticator.authenticateAndGenerateToken(requestDto);
        return ResponseEntity.ok(jwtResponse);
    }
}
