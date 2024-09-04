package com.joboffers.infrastructure.usermanagement.controller;

import com.joboffers.domain.usersmanagement.UsersManagementFacade;
import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@AllArgsConstructor
class UserManagementRestController {
    
    private final UsersManagementFacade usersManagementFacade;
    
    @PostMapping("register")
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody @Valid UserRequestDto requestDto) {
        UserRegistrationResponseDto saved = usersManagementFacade.saveUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
