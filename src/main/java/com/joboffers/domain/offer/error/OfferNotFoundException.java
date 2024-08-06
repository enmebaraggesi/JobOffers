package com.joboffers.domain.offer.error;

public class OfferNotFoundException extends RuntimeException {
    
    public OfferNotFoundException(final String message) {
        super(message);
    }
}
