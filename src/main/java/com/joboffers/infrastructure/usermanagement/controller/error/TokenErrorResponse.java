package com.joboffers.infrastructure.usermanagement.controller.error;

import org.springframework.http.HttpStatus;

public record TokenErrorResponse(String message, HttpStatus status) {

}
