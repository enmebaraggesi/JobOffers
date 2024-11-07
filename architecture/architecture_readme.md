## Architecture

Application is made as modular monolith. Every module should take care over one feature.

### There are three modules:
* **Offer** - taking care of offers API, storing offers, periodically fetching new ones to database
  * **Cache** - additional module to store offers once searched for 1 hour to speed up receiving results
* **UsersManagement** - taking care of new user registration, finding users
* **Security** - taking care of authentication user, JSON Web Token generation and binding it to user

### Offer module endpoints:
* **GET /offers** - giving list of all available job offers to user. _User needs to be registered and obtain JWT before use._
* **GET /offers/{id}** - giving specific offer by its database ID. _User needs to be registered and obtain JWT before use._
* **POST /offers** - adding new offer to database (consumes JSON with link, position, company_name and salary). _User needs to be registered and obtain JWT before use._

### UsersManagement module endpoints:
* **POST /register** - adding new user to database (consumes JSON with distinct name, email and password). _This endpoint is open and can be used without JWT._

### JwtAuthenticator module endpoints:
* **POST /token** - generating new token and binding to user (consumes JSON with username and password). _This endpoint is open and can be used without JWT._

### Swagger utility address:
* **http://localhost:8080/swagger-ui/index.html#/** - _this one is also open and can be used without JWT and registration._