package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UsersManagementFacade {
    
    private final UsersRepository repository;
    
    public List<UserResponseDto> findAll() {
        List<User> users = repository.findAll();
        return UserMapper.mapUserListToUserResponseDtoList(users);
    }
}
