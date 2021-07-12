# ad_server_spring_sample
To build the application, run `mvn clean install`

To run the application locally, run the command `mvn spring-boot:run`.  The hostname when you run this way we be `localhost:8080`

The endpoints for this application are as follows:

- **POST** - `/ad`: Adds an ad to the ad server. If there is an existing ad for the partner associated with the ad, this will give an error unless the existing ad is expired. If the existing ad is expired, then the expired ad will be overwritten with the ad given in the body of this POST request
- **GET** - `/ad/{partner_id}`: Retrieves and ad for a given partner ID. Will give an error if the ad has expired.
- **GET** - `/all`: Retrieves all ads for registered in the server. This includes expired ads.
