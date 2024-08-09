package com.joboffers.domain.offer;

enum ResponseMessage {
    OFFER_NOT_FOUND("Offer not found"),
    DUPLICATE_URL("There is already an offer with such url");
    
    final String message;
    
    ResponseMessage(final String message) {
        this.message = message;
    }
}
