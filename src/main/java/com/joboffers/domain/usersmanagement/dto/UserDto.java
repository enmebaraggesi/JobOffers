package com.joboffers.domain.usersmanagement.dto;

import lombok.Builder;

@Builder
public record UserDto(String id, String name, String email, String password) {

}
