package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import com.joboffers.infrastructure.usermanagement.controller.dto.UserRegistrationRequestDto;

import java.util.List;

class UserMapper {
    
    static List<UserResponseDto> mapUserListToUserResponseDtoList(final List<User> users) {
        return users.stream()
                    .map(UserMapper::mapUserToUserResponseDto)
                    .toList();
    }
    
    static UserResponseDto mapUserToUserResponseDto(final User user) {
        return UserResponseDto.builder()
                              .id(user.id())
                              .name(user.name())
                              .email(user.email())
                              .build();
    }
    
    static User mapUserRegistrationRequestDtoToUser(final UserRegistrationRequestDto requestDto) {
        return User.builder()
                   .name(requestDto.username())
                   .email(requestDto.email())
                   .password(requestDto.password())
                   .build();
    }
    
    static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                      .id(user.id())
                      .name(user.name())
                      .email(user.email())
                      .password(user.password())
                      .build();
    }
}
