package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserRequestDto;
import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import com.joboffers.domain.usersmanagement.error.UserNotFoundException;
import org.junit.jupiter.api.Test;

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
    void should_throw_exception_when_there_is_no_user_with_such_id() {
        //given
        Long id = 1L;
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        assertThat(facade.findAllUsers()).isEmpty();
        //when
        Exception caught = catchException(() -> facade.findUserById(id));
        //then
        assertThat(caught).isInstanceOf(UserNotFoundException.class);
        assertThat(caught.getMessage()).isEqualTo("No user found with id " + id);
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
        UserResponseDto response = facade.saveUser(requestDto);
        //then
        UserResponseDto expected = UserResponseDto.builder()
                                                  .id(1L)
                                                  .name("user1")
                                                  .email("user1@email.com")
                                                  .password("password1")
                                                  .build();
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(expected);
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
        UserResponseDto response1 = facade.saveUser(requestDto1);
        UserResponseDto response2 = facade.saveUser(requestDto2);
        //then
        UserResponseDto expected1 = UserResponseDto.builder()
                                                   .id(1L)
                                                   .name("user1")
                                                   .email("user1@email.com")
                                                   .password("password1")
                                                   .build();
        UserResponseDto expected2 = UserResponseDto.builder()
                                                   .id(2L)
                                                   .name("user2")
                                                   .email("user2@email.com")
                                                   .password("password2")
                                                   .build();
        assertThat(facade.findAllUsers()).hasSize(2);
        assertThat(response1).isEqualTo(expected1);
        assertThat(response2).isEqualTo(expected2);
    }
    
    @Test
    void should_find_user_by_id() {
        //given
        Long id = 1L;
        UserRequestDto requestDto = UserRequestDto.builder()
                                                  .name("user1")
                                                  .email("user1@email.com")
                                                  .password("password1")
                                                  .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        facade.saveUser(requestDto);
        assertThat(facade.findAllUsers()).hasSize(1);
        //when
        UserResponseDto response = facade.findUserById(id);
        //then
        UserResponseDto expected = UserResponseDto.builder()
                                                  .id(id)
                                                  .name("user1")
                                                  .email("user1@email.com")
                                                  .password("password1")
                                                  .build();
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(expected);
    }
}