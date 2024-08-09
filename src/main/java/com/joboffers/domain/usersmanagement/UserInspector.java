package com.joboffers.domain.usersmanagement;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class UserInspector {
    
    private final UsersRepository repository;
    
    boolean inspectRegistrationRequest(final User user) {
        return isNameValid(user.name()) && isEmailValid(user.email()) && isPasswordValid(user.password());
    }
    
    private boolean isNameValid(final String name) {
        return name != null && !name.isEmpty() && !repository.existsByName(name);
    }
    
    private boolean isEmailValid(final String email) {
        return email != null && !email.isEmpty() && !repository.existsByEmail(email);
    }
    
    private boolean isPasswordValid(final String password) {
        return password != null && !password.isEmpty();
    }
}
