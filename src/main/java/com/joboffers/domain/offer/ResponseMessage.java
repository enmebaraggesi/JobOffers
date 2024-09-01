package com.joboffers.domain.offer;

enum ResponseMessage {
    OFFER_NOT_FOUND("Offer with ID %s not found"),
    DUPLICATE_URL("There is already an offer with URL: %s");
    
    final private String message;
    
    ResponseMessage(final String message) {
        this.message = message;
    }
    
    public String format(String string) {
        return String.format(message, string);
    }
}
