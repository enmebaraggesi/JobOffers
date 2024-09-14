package com.joboffers.infrastructure.security.jwtauthenticator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.jwt")
public record JwtConfigurationProperties(String secret,
                                         long expirationDs,
                                         String issuer) {
    
}
