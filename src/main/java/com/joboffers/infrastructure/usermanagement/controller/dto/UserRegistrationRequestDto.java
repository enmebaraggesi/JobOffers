package com.joboffers.infrastructure.usermanagement.controller.dto;

import lombok.Builder;

@Builder
public record UserRegistrationRequestDto(String username, String password, String email) {

}
