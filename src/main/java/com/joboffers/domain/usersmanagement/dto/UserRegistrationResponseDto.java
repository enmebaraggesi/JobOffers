package com.joboffers.domain.usersmanagement.dto;

import lombok.Builder;

@Builder
public record UserRegistrationResponseDto(Long id,
                                          String name,
                                          boolean created) {

}
