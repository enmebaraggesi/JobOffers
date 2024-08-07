package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import com.joboffers.domain.usersmanagement.error.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.joboffers.domain.usersmanagement.ResponseMessage.USER_NOT_FOUND;

@AllArgsConstructor
public class UsersManagementFacade {
    
    private final UsersRepository repository;
    private final UserInspector inspector;
    
    public List<UserResponseDto> findAllUsers() {
        List<User> users = repository.findAll();
        return UserMapper.mapUserListToUserResponseDtoList(users);
    }
    
    UserResponseDto findUserByName(final String name) {
        return repository.findByName(name)
                         .map(UserMapper::mapUserToUserResponseDto)
                         .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.message));
    }
    
    UserResponseDto findUserById(final Long id) {
        return repository.findById(id)
                         .map(UserMapper::mapUserToUserResponseDto)
                         .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.message));
    }
    
    UserRegistrationResponseDto saveUser(final UserRequestDto requestDto) {
        User user = UserMapper.mapUserRequestDtoToUser(requestDto);
        if (inspector.inspectRegistrationRequest(user)) {
            User saved = repository.save(user);
            return new UserRegistrationResponseDto(saved.id(), saved.name(), true);
        }
        return new UserRegistrationResponseDto(null, user.name(), false);
    }
}
