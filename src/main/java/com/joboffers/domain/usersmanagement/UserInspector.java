package com.joboffers.domain.usersmanagement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
class UserInspector {
    
    private final UsersRepository repository;
    private List<String> errors = new ArrayList<>();
    
    boolean inspectRegistrationRequest(final User user) {
        return isNameValid(user.name()) & isEmailValid(user.email());
    }
    
    private boolean isNameValid(final String name) {
        if (repository.existsByName(name)) {
            errors.add("Username: " + name + " already exists");
            return false;
        }
        return true;
    }
    
    private boolean isEmailValid(final String email) {
        if (repository.existsByEmail(email)) {
            errors.add("Email: " + email + " already exists");
            return false;
        }
        return true;
    }
}
