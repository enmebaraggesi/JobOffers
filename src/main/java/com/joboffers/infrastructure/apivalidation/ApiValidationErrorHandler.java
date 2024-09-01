package com.joboffers.infrastructure.apivalidation;

import com.joboffers.domain.offer.error.DuplicateOfferUrlException;
import com.joboffers.infrastructure.apivalidation.dto.ApiValidationResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
class ApiValidationErrorHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiValidationResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = getAllExceptions(e);
        return new ApiValidationResponseDto(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DuplicateOfferUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiValidationResponseDto handleDuplicateOfferUrlException(DuplicateOfferUrlException e) {
        String message = e.getMessage();
        return new ApiValidationResponseDto(List.of(message), HttpStatus.BAD_REQUEST);
    }
    
    private List<String> getAllExceptions(final BindException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
