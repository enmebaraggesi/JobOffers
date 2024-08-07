package com.joboffers.domain.usersmanagement;

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
                              .password(user.password())
                              .build();
    }
}
