package com.joboffers.infrastructure.security.jwtauthenticator;

import com.joboffers.domain.usersmanagement.UsersManagementFacade;
import com.joboffers.domain.usersmanagement.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    
    private final UsersManagementFacade usersManagementFacade;
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserDto userDto = usersManagementFacade.findUserByName(username);
        return getUser(userDto);
    }
    
    private UserDetails getUser(final UserDto userDto) {
        return new User(userDto.name(), userDto.password(), Collections.emptyList());
    }
}
