package com.joboffers.infrastructure.security.token.error;

import org.springframework.http.HttpStatus;

public record TokenErrorResponse(String message, HttpStatus status) {

}
