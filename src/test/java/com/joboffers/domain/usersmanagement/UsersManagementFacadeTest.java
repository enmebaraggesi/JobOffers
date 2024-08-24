package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserRegistrationResponseDto;
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
        String id = "abc";
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        assertThat(facade.findAllUsers()).isEmpty();
        //when
        Exception caught = catchException(() -> facade.findUserById(id));
        //then
        assertThat(caught).isInstanceOf(UserNotFoundException.class);
        assertThat(caught.getMessage()).isEqualTo("User not found");
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
        assertThat(caught).isInstanceOf(UserNotFoundException.class);
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
    void should_find_user_by_id() {
        //given
        UserRequestDto requestDto = UserRequestDto.builder()
                                                  .name("user1")
                                                  .email("user1@email.com")
                                                  .password("password1")
                                                  .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        UserRegistrationResponseDto saved = facade.saveUser(requestDto);
        assertThat(facade.findAllUsers()).hasSize(1);
        //when
        UserResponseDto response = facade.findUserById(saved.id());
        //then
        assertThat(response).isNotNull()
                            .isEqualTo(UserResponseDto.builder()
                                                      .id(saved.id())
                                                      .name("user1")
                                                      .email("user1@email.com")
                                                      .build());
    }
    
    @Test
    void should_find_user_by_name() {
        //given
        String name = "user1";
        UserRequestDto requestDto = UserRequestDto.builder()
                                                  .name(name)
                                                  .email("user1@email.com")
                                                  .password("password1")
                                                  .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        UserRegistrationResponseDto saved = facade.saveUser(requestDto);
        assertThat(facade.findAllUsers()).hasSize(1);
        //when
        UserResponseDto response = facade.findUserByName(name);
        //then
        assertThat(response).isNotNull()
                            .isEqualTo(UserResponseDto.builder()
                                                      .id(saved.id())
                                                      .name(name)
                                                      .email("user1@email.com")
                                                      .build());
    }
    
    @Test
    void should_not_register_user_while_given_name_email_or_password_is_null() {
        //given
        UserRequestDto nullNameRequest = UserRequestDto.builder()
                                                       .name(null)
                                                       .email("user1@email.com")
                                                       .password("password1")
                                                       .build();
        UserRequestDto nullEmailRequest = UserRequestDto.builder()
                                                        .name("user1")
                                                        .email(null)
                                                        .password("password1")
                                                        .build();
        UserRequestDto nullPasswordRequest = UserRequestDto.builder()
                                                           .name("user1")
                                                           .email("user1@email.com")
                                                           .password(null)
                                                           .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        //when
        UserRegistrationResponseDto nullNameResponse = facade.saveUser(nullNameRequest);
        UserRegistrationResponseDto nullEmailResponse = facade.saveUser(nullEmailRequest);
        UserRegistrationResponseDto nullPasswordResponse = facade.saveUser(nullPasswordRequest);
        //then
        assertThat(nullNameResponse.id()).isNull();
        assertThat(nullNameResponse.created()).isFalse();
        assertThat(nullEmailResponse.id()).isNull();
        assertThat(nullEmailResponse.created()).isFalse();
        assertThat(nullPasswordResponse.id()).isNull();
        assertThat(nullPasswordResponse.created()).isFalse();
    }
    
    @Test
    void should_not_register_user_while_given_name_email_or_password_is_empty() {
        //given
        UserRequestDto emptyNameRequest = UserRequestDto.builder()
                                                        .name("")
                                                        .email("user1@email.com")
                                                        .password("password1")
                                                        .build();
        UserRequestDto emptyEmailRequest = UserRequestDto.builder()
                                                         .name("user1")
                                                         .email("")
                                                         .password("password1")
                                                         .build();
        UserRequestDto emptyPasswordRequest = UserRequestDto.builder()
                                                            .name("user1")
                                                            .email("user1@email.com")
                                                            .password("")
                                                            .build();
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        //when
        UserRegistrationResponseDto emptyNameResponse = facade.saveUser(emptyNameRequest);
        UserRegistrationResponseDto emptyEmailResponse = facade.saveUser(emptyEmailRequest);
        UserRegistrationResponseDto emptyPasswordResponse = facade.saveUser(emptyPasswordRequest);
        //then
        assertThat(emptyNameResponse.id()).isNull();
        assertThat(emptyNameResponse.created()).isFalse();
        assertThat(emptyEmailResponse.id()).isNull();
        assertThat(emptyEmailResponse.created()).isFalse();
        assertThat(emptyPasswordResponse.id()).isNull();
        assertThat(emptyPasswordResponse.created()).isFalse();
    }
}