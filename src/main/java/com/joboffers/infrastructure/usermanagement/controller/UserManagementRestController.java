package com.joboffers.infrastructure.usermanagement.controller;

import com.joboffers.domain.usersmanagement.UsersManagementFacade;
import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import com.joboffers.infrastructure.usermanagement.controller.dto.UserRegistrationRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class UserManagementRestController {
    
    private final UsersManagementFacade usersManagementFacade;
    private final PasswordEncoder bCryptPasswordEncoder;
    
    @PostMapping("register")
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody @Valid UserRequestDto requestDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(requestDto.password());
        UserRegistrationResponseDto saved = usersManagementFacade.saveUser(
                UserRegistrationRequestDto.builder()
                                          .username(requestDto.name())
                                          .email(requestDto.email())
                                          .password(encodedPassword)
                                          .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
