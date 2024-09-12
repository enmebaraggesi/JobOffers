package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserDto;
import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class UsersManagementFacadeTest {
    
    @Test
    void should_find_no_users_while_there_are_no_users_in_db() {
        //given
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        //when
        List<UserResponseDto> response = facade.findAllUsers();
        //then
        assertThat(response).isEmpty();
    }
    
    @Test
    void should_throw_exception_when_there_is_no_user_with_such_name() {
        //given
        String name = "user1";
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        assertThat(facade.findAllUsers()).isEmpty();
        //when
        Exception caught = catchException(() -> facade.findUserByName(name));
        //then
        assertThat(caught).isInstanceOf(UsernameNotFoundException.class);
        assertThat(caught.getMessage()).isEqualTo("User not found");
    }
    
    @Test
    void should_save_user_properly() {
        //given
        UserRequestDto requestDto = UserRequestDto.builder()
                                                  .name("user1")
                                                  .email("user1@email.com")
                                                  .password("password1")
                                                  .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        assertThat(facade.findAllUsers()).isEmpty();
        //when
        UserRegistrationResponseDto response = facade.saveUser(requestDto);
        //then
        assertThat(response).isNotNull();
        assertThat(response.created()).isTrue();
        assertThat(response.name()).isEqualTo("user1");
    }
    
    @Test
    void should_save_two_users_properly() {
        //given
        UserRequestDto requestDto1 = UserRequestDto.builder()
                                                   .name("user1")
                                                   .email("user1@email.com")
                                                   .password("password1")
                                                   .build();
        UserRequestDto requestDto2 = UserRequestDto.builder()
                                                   .name("user2")
                                                   .email("user2@email.com")
                                                   .password("password2")
                                                   .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        assertThat(facade.findAllUsers()).isEmpty();
        //when
        UserRegistrationResponseDto response1 = facade.saveUser(requestDto1);
        UserRegistrationResponseDto response2 = facade.saveUser(requestDto2);
        //then
        assertThat(facade.findAllUsers()).hasSize(2);
        assertThat(response1).isEqualTo(UserRegistrationResponseDto.builder()
                                                                   .id(response1.id())
                                                                   .name("user1")
                                                                   .created(true)
                                                                   .build());
        assertThat(response2).isEqualTo(UserRegistrationResponseDto.builder()
                                                                   .id(response2.id())
                                                                   .name("user2")
                                                                   .created(true)
                                                                   .build());
    }
    
    @Test
    void should_find_user_by_name() {
        //given
        String name = "user1";
        String mail = "user1@email.com";
        String password = "password1";
        UserRequestDto requestDto = UserRequestDto.builder()
                                                  .name(name)
                                                  .email(mail)
                                                  .password(password)
                                                  .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        UserRegistrationResponseDto saved = facade.saveUser(requestDto);
        assertThat(facade.findAllUsers()).hasSize(1);
        //when
        UserDto response = facade.findUserByName(name);
        //then
        assertThat(response).isNotNull()
                            .isEqualTo(UserDto.builder()
                                              .id(saved.id())
                                              .name(name)
                                              .email(mail)
                                              .password(password)
                                              .build());
    }
}