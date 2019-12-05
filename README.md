# Pirates

## Introduction and prerequisites
Pirates is a REST API implemented in Java using Spring Boot. It uses Gradle as its build system and PostgreSQL as its database engine. It will need then
the following software to be installed in order to run properly the application:
* Java 12
* Gradle 5.4.1
* PostgreSQL 11.3

## How to run the Pirates application
1. First of all, the database has to be configured:
    * run `createdb 'pirates'` to create the Pirates database on the PostgreSQL instance.
    * run `psql -h localhost -d pirates` to access the database.
    * from the database terminal, run `create user oneeyed with encrypted password '123456'` to create the user.
2. Clone the project.
3. From the root directory, run the Pirates application with the `./gradlew bootRun` command. It will automatically deploy all the databases changes using Liquibase and
it will initialize the database with some sample ships and ports.
4. The application will start up on the 8080 port. Check on the browser that the application works correctly with the following URL: `http://localhost:8080/api/v1_0/port/1`.

## REST API
On the docs folder, you will find the Postman configuration file that contains all the endpoints that the Pirates REST API contains. They are three different endpoints:
