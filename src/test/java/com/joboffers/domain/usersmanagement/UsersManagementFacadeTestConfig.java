package com.joboffers.domain.usersmanagement;

class UsersManagementFacadeTestConfig {
    
    static UsersManagementFacade createForTest() {
        UsersRepository repository = new UsersRepositoryTestImpl();
        return new UsersManagementFacade(repository);
    }
}
