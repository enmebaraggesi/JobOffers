package com.joboffers.infrastructure.apivalidation;

import com.joboffers.infrastructure.apivalidation.dto.ApiValidationResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
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
    
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiValidationResponseDto handleDuplicateKeyException(DuplicateKeyException e) {
        String message = "There is already an offer with such URL";
        return new ApiValidationResponseDto(List.of(message), HttpStatus.CONFLICT);
    }
    
    private List<String> getAllExceptions(final BindException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
