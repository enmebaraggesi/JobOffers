package com.joboffers;

public interface SampleJobOffersTestResponse {
    
    default String zeroOffersResponseJson() {
        return "[]".trim();
    }
}
