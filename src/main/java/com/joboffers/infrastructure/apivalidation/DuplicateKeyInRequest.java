package com.joboffers.infrastructure.apivalidation;

public class DuplicateKeyInRequest extends RuntimeException {
    
    public DuplicateKeyInRequest(String message) {
        super(message);
    }
}
