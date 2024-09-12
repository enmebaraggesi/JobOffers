package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserDto;
import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import com.joboffers.domain.usersmanagement.error.DuplicateUserCredentialsException;
import com.joboffers.infrastructure.usermanagement.controller.dto.UserRegistrationRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

@AllArgsConstructor
public class UsersManagementFacade {
    
    private static final String USER_NOT_FOUND = "User not found";
    
    private final UsersRepository repository;
    private final UserInspector inspector;
    
    public List<UserResponseDto> findAllUsers() {
        List<User> users = repository.findAll();
        return UserMapper.mapUserListToUserResponseDtoList(users);
    }
    
    public UserDto findUserByName(final String name) {
        return repository.findByName(name)
                         .map(UserMapper::mapUserToUserDto)
                         .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND));
    }
    
    public UserRegistrationResponseDto saveUser(final UserRegistrationRequestDto requestDto) {
        User user = UserMapper.mapUserRegistrationRequestDtoToUser(requestDto);
        if (inspector.inspectRegistrationRequest(user)) {
            User saved = repository.save(user);
            return new UserRegistrationResponseDto(saved.id(), saved.name(), true);
        } else {
            List<String> errors = inspector.getErrors();
            String message = String.join(", ", errors);
            throw new DuplicateUserCredentialsException(message);
        }
    }
}
