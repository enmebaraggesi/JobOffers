package com.joboffers.infrastructure.apivalidation.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationResponseDto(List<String> errors, HttpStatus status) {

}
