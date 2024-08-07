package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UsersManagementFacadeTest {
    
    @Test
    void should_find_no_users_while_there_are_no_users_in_db() {
        //given
        UsersManagementFacade facade = UsersManagementFacadeTestConfig.createForTest();
        //when
        List<UserResponseDto> response = facade.findAll();
        //then
        assertThat(response).isEmpty();
    }
}