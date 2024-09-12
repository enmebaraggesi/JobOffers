package com.joboffers.infrastructure.security.config;

import com.joboffers.domain.usersmanagement.UsersManagementFacade;
import com.joboffers.infrastructure.security.jwtauthenticator.LoginUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {
    
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public UserDetailsService userDetailsService(final UsersManagementFacade usersManagementFacade) {
        return new LoginUserDetailsService(usersManagementFacade);
    }
}
