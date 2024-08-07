package com.joboffers.domain.usersmanagement;

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
}