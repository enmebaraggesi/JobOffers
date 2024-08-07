package com.joboffers.domain.usersmanagement.error;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(final String message) {
        super(message);
    }
}
