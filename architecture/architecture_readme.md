## Architecture

Application is made as modular monolith. Every module should take care over one feature.

### There are three modules:
* **Offer** - taking care of offers API, storing offers, periodically fetching new ones to database
* **UsersManagement** - taking care of new user registration, finding users
* **JwtAuthenticator** - taking care of authentication user, token generation and binding to user

### Offer module endpoints:
* **GET /offers** - giving list of all available job offers to user
* **GET /offers/{id}** - giving specific offer by its database ID
* **POST /offers** - adding new offer to database (consumes JSON with link, position, company_name and salary)

### UsersManagement module endpoints:
* **POST /register** - adding new user to database (consumes JSON with username and password)

### JwtAuthenticator module endpoints:
* **POST /token** - generating new token and binding to user