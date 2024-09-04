package com.joboffers.domain.usersmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRequestDto(
        @NotNull(message = "{name.not.null}")
        @NotEmpty(message = "{name.not.empty}")
        String name,
        @NotNull(message = "{email.not.null}")
        @NotEmpty(message = "{email.not.empty}")
        String email,
        @NotNull(message = "{password.not.null}")
        @NotEmpty(message = "{password.not.empty}")
        String password) {
    
}
