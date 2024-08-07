package com.joboffers.domain.offer.error;

public class DuplicateOfferUrlException extends RuntimeException {
    
    public DuplicateOfferUrlException(final String message) {
        super(message);
    }
}
