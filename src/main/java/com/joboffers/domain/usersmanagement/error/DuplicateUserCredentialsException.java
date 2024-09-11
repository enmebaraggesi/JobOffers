package com.joboffers.domain.usersmanagement.error;

import com.joboffers.infrastructure.apivalidation.DuplicateKeyInRequest;

public class DuplicateUserCredentialsException extends DuplicateKeyInRequest {
    
    public DuplicateUserCredentialsException(final String message) {
        super(message);
    }
}
