# JOBOFFERS
## JobOffers is an application allowing user to search for Junior Java Developer job offers. Offers are saved from remote HTTP server (using script that fetches assets from WWW).

### As a client:
* you must use an authorisation token to use JobOffers
* you can register and therefore obtain token
* you can fetch one offer by its ID from database
* you can fetch all available offers from database
* you can come back every 3 hours to look after new offers (database is updated in 3 hours intervals)
* you shouldn't see any duplicated offers (offers in database must be unique by URL to offer)
* whenever you make more than one request in 60 minutes all data should come from cache (to save operation time)
* you can upload your job offer
* every job offer has its link to original post, position title, company name and salary