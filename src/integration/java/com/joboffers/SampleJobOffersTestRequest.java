package com.joboffers;

public interface SampleJobOffersTestRequest {
    
    default String offerRequestJson() {
        return """
               {
               "position": "Test position",
               "company": "Test company",
               "salary": "9999 USD",
               "url": "https://joboffers.com"
               }
               """.trim();
    }
}
