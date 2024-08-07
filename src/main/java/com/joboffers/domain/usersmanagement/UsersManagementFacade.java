package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import com.joboffers.domain.usersmanagement.error.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UsersManagementFacade {
    
    private final UsersRepository repository;
    
    public List<UserResponseDto> findAllUsers() {
        List<User> users = repository.findAll();
        return UserMapper.mapUserListToUserResponseDtoList(users);
    }
    
    UserResponseDto findUserById(final Long id) {
        return repository.findById(id)
                         .map(UserMapper::mapUserToUserResponseDto)
                         .orElseThrow(() -> new UserNotFoundException("No user found with id " + id));
    }
    
    UserResponseDto saveUser(final UserRequestDto requestDto) {
        User user = UserMapper.mapUserRequestDtoToUser(requestDto);
        User saved = repository.save(user);
        return UserMapper.mapUserToUserResponseDto(saved);
    }
}
