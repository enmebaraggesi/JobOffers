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
    
    default String offerWithExistingUrlRequestJson() {
        return """
               {
               "position": "New position",
               "company": "New company",
               "salary": "9999 PLN",
               "url": "https://joboffers.com"
               }
               """.trim();
    }
    
    default String offerWithEmptyPositionAndSalaryRequestJson() {
        return """
               {
               "position": "",
               "company": "Test company",
               "salary": "",
               "url": "https://joboffers.com"
               }
               """.trim();
    }
    
    default String offerWithNullCompanyRequestJson() {
        return """
               {
               "position": "Test position",
               "salary": "9999 USD",
               "url": "https://joboffers.com"
               }
               """.trim();
    }
    
    default String userRegistrationRequestJson() {
        return """
               {
               "name": "test",
               "email": "test@test.com",
               "password": "testpassword"
               }
               """.trim();
    }
    
    default String userRegistrationEmptyNameAndPasswordRequestJson() {
        return """
               {
               "name": "",
               "email": "test@test.com",
               "password": ""
               }
               """.trim();
    }
    
    default String userRegistrationNullEmailRequestJson() {
        return """
               {
               "name": "test",
               "password": "testpassword"
               }
               """.trim();
    }
    
    default String tokenRequestJson() {
        return """
               {
               "username": "test",
               "password": "testpassword"
               }
               """.trim();
    }
}
