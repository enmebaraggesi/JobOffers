package com.joboffers.infrastructure.security.config;

import com.joboffers.domain.usersmanagement.UsersManagementFacade;
import com.joboffers.infrastructure.security.jwtauthenticator.LoginUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(final UsersManagementFacade usersManagementFacade) {
        return new LoginUserDetailsService(usersManagementFacade);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                           .httpBasic(AbstractHttpConfigurer::disable)
                           .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                           .authorizeHttpRequests(auth -> auth
                                   .requestMatchers("/swagger-ui/**").permitAll()
                                   .requestMatchers("/v3/api-docs/**").permitAll()
                                   .requestMatchers("/webjars/**").permitAll()
                                   .requestMatchers("/token/**").permitAll()
                                   .requestMatchers("/register/**").permitAll()
                                   .requestMatchers("/swagger-resources/**").permitAll()
                                   .anyRequest().authenticated()
                           )
                           .build();
    }
}
