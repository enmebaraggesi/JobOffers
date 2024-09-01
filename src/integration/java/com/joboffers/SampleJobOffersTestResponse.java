package com.joboffers;

public interface SampleJobOffersTestResponse {
    
    default String zeroOffersResponseJson() {
        return "[]".trim();
    }
    
    default String twoOffersResponseJson() {
        return """
               [
                   {
                       "title": "Software Engineer - Mobile (m/f/d)",
                       "company": "Cybersource",
                       "salary": "4k - 8k PLN",
                       "offerUrl": "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn"
                   },
                   {
                       "title": "Junior DevOps Engineer",
                       "company": "CDQ Poland",
                       "salary": "8k - 14k PLN",
                       "offerUrl": "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd"
                   }
               ]
               """.trim();
    }
    
    default String nonExistingOfferResponseJson() {
        return """
               {
               "message": "Offer with ID nonExistingId not found",
               "status": "NOT_FOUND"
               }
               """.trim();
    }
}
