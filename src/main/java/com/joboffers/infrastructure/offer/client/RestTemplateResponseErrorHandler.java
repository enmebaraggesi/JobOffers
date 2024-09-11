package com.joboffers.infrastructure.offer.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Log4j2
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
    
    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is5xxServerError()) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Client problem during external server connection");
        } else if (statusCode.is4xxClientError()) {
            if (statusCode.value() == NOT_FOUND.value()) {
                throw new ResponseStatusException(NOT_FOUND);
            } else if (statusCode.value() == UNAUTHORIZED.value()) {
                throw new ResponseStatusException(UNAUTHORIZED);
            }
        }
    }
}
