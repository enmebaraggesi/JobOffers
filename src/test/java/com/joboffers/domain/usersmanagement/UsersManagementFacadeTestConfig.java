package com.joboffers.domain.usersmanagement;

class UsersManagementFacadeTestConfig {
    
    static UsersManagementFacade createForTest() {
        UsersRepository repository = new UsersRepositoryTestImpl();
        UserInspector inspector = new UserInspector(repository);
        return new UsersManagementFacade(repository, inspector);
    }
}
