package com.joboffers.infrastructure.apivalidation;

import com.joboffers.infrastructure.apivalidation.dto.ApiValidationResponseDto;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
class ApiValidationErrorHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiValidationResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = getAllExceptions(e);
        return new ApiValidationResponseDto(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DuplicateKeyInRequest.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiValidationResponseDto handleDuplicateKeyException(DuplicateKeyInRequest e) {
        return new ApiValidationResponseDto(List.of(e.getMessage()), HttpStatus.CONFLICT);
    }
    
    private List<String> getAllExceptions(final BindException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
