package com.joboffers.domain.offer;

enum ResponseMessage {
    OFFER_NOT_FOUND("Offer with ID %s not found");
    
    final private String message;
    
    ResponseMessage(final String message) {
        this.message = message;
    }
    
    public String format(String string) {
        return String.format(message, string);
    }
}
