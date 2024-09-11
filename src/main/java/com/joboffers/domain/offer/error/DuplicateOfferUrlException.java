package com.joboffers.domain.offer.error;

import com.joboffers.infrastructure.apivalidation.DuplicateKeyInRequest;

public class DuplicateOfferUrlException extends DuplicateKeyInRequest {
    
    public DuplicateOfferUrlException(final String message) {
        super(message);
    }
}
