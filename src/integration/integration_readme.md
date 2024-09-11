INTEGRATION TEST PATH:

1. There are no offers to fetch from external source
2. Scheduler runs 1st time making GET request to external source adding 0 offers to database
3. User tries to obtain JWT token making POST request to /token, but system returns UNAUTHORIZED(401)
4. User tries to find offers with no JWT token making GET request to /offers, but system returns UNAUTHORIZED(401)
5. User registers successfully making POST request to /register giving username, password and email
6. User tries to obtain JWT token making POST request to /token with username and password successfully
7. Registered and authorized user makes GET request to /offers with header "Authorization: Bearer {token}" and system
   returns OK(200) with 0 offers
8. There are 2 new offers to fetch from external source
9. Scheduler runs 2nd time making GET request to external source adding 2 offers to database
10. User makes GET request to /offers with header “Authorization: Bearer {token}” and system returns OK(200) with 2 new
    offers
11. User makes GET request to /offers/{id} with authorization header and non-existing ID and system returns NOT_FOUND(
    404) with message "Offer with ID {id} not found"
12. User makes GET request to /offers/{id} with authorization header and existing ID and system returns OK(200) with
    exact offer
13. There are 2 new offers to fetch from external source
14. Scheduler runs 3rd time making GET request to external source adding 2 offers to database
15. User makes GET request to /offers with authorization header and system returns OK(200) with 4 offers