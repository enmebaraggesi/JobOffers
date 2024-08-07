package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;

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
    
    static User mapUserRequestDtoToUser(final UserRequestDto requestDto) {
        return User.builder()
                   .name(requestDto.name())
                   .email(requestDto.email())
                   .password(requestDto.password())
                   .build();
    }
    
    static UserRegistrationResponseDto mapUserRegistrationResponseDto(final User user, boolean created) {
        return UserRegistrationResponseDto.builder()
                                          .id(user.id())
                                          .name(user.name())
                                          .created(created)
                                          .build();
    }
}
