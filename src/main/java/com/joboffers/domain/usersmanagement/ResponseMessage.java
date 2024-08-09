package com.joboffers.domain.usersmanagement;

enum ResponseMessage {
    USER_NOT_FOUND("User not found");
    
    final String message;
    
    ResponseMessage(final String message) {
        this.message = message;
    }
}
