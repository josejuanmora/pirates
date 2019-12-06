# Pirates

## Introduction and prerequisites
Pirates is a REST API implemented in Java using Spring Boot. It uses Gradle as its build system and PostgreSQL as its database engine. It will need then
the following software to be installed in order to run properly the application:

    * Java 12
    * Gradle 5.4.1
    * PostgreSQL 11.3

## Running the Pirates application
1. First of all, the database has to be configured:
    * run `createdb 'pirates'` to create the Pirates database on the PostgreSQL instance.
    * run `psql -h localhost -d pirates` to access the database.
    * from the database terminal, run `create user oneeyed with encrypted password '123456'` to create the user.
2. Clone the project.
3. From the root directory, run the Pirates application with the `./gradlew bootRun` command. It will automatically deploy all the databases changes using Liquibase and
it will initialize the database with some sample ships and ports.
4. The application will start up on the 8080 port. Check on the browser that the application works correctly with the following URL: `http://localhost:8080/api/v1_0/port/1`.

## REST API
On the docs folder, you will find the Postman configuration file that contains all the endpoints that the Pirates REST API contains. There are three different endpoints:

1. **Retrieve ship history**. The endpoint is `http://localhost:8080/api/v1_0/ship/{id}` where *id* is the id of the ship, using the GET http method. It displays basic information of the ship as well as the
history of events (arrivals and departures from ports). It supports an optional *eventType* parameter that supports both *DEPARTURE_FROM_PORT* and *ARRIVAL_TO_PORT* in order to
filter events from that type. Returns a 200 http status code if ok, 404 http status if no ship is found.
2. **Retrieve port history**. The endpoint is `http://localhost:8080/api/v1_0/port/{id}` where *id* is the id of the port, using the GET http method. It displays basic information of the port as well as the
history of events (arrivals and departures of ships). It supports an optional *eventType* parameter that supports both *DEPARTURE_FROM_PORT* and *ARRIVAL_TO_PORT* in order to
filter events from that type. Returns a 200 http status code if ok, 404 http status if no port is found.
3. **Create an event**. The endpoint is `http://localhost:8080/api/v1_0/ship/{id}/event` where id is the id of the ship, using the PUT http method. The body has to include the following information:
`{ "portId": {portId}, "eventType": "{ARRIVAL_TO_PORT|DEPARTURE_FROM_PORT}", "stock" : { "barrelsOfRum": {barrels} ,"goldCoins": {coins}  } }`
Returns a 201 http status code if ok and a 400 http status code if the information is not valid.

## Known constraints
It is assumed that the ship is on the high seas for the first time. That is, the first event to be created for a ship has to be a *ARRIVAL_TO_PORT* event. Moreover, it has been considered that no other goods
different than barrels of rum or gold coins should be supported. If that would be the case, it may be considered a different way to store the goods to avoid undesired modifications of the
data model.