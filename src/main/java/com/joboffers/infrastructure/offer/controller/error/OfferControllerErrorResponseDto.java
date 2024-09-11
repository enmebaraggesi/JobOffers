package com.joboffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;

public record OfferControllerErrorResponseDto(String message, HttpStatus status) {

}
