package com.joboffers.domain.usersmanagement;

import com.joboffers.domain.usersmanagement.dto.UserRequestDto;

class UserInspector {
    
    boolean inspectRegistrationRequest(final UserRequestDto dto) {
        if (isNameInvalid(dto)) {
            return false;
        } else if (isPasswordInvalid(dto)) {
            return false;
        } else return isEmailValid(dto);
    }
    
    private boolean isEmailValid(final UserRequestDto dto) {
        return dto.email() != null && !dto.email().isEmpty();
    }
    
    private boolean isPasswordInvalid(final UserRequestDto dto) {
        return dto.password() == null || dto.password().isEmpty();
    }
    
    private boolean isNameInvalid(final UserRequestDto dto) {
        return dto.name() == null || dto.name().isEmpty();
    }
}

//todo implement database search for duplicates
