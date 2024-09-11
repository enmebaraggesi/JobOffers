package com.joboffers.domain.usersmanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UsersManagementFacadeConfig {
    
    @Bean
    public UsersManagementFacade usersManagementFacade(UsersRepository repository) {
        UserInspector inspector = new UserInspector(repository);
        return new UsersManagementFacade(repository, inspector);
    }
}
