## Overview
This Spring Boot application provides a REST API endpoint that returns GitHub user profile and repository data.

## To Run
1. Clone this repo 
```bash
git clone https://github.com/eai500/github-users.git
```
2. Build the project
```bash
mvn clean install
```
3. Run the project via command line or your IDE's run button
```bash
mvn spring-boot:run
```


## To Test
Run the app locally and enter the following URL in your browser or a REST Client like Postman
```
GET http://localhost:8080/github-users/octocat
```

Run the unit tests and functional tests with the following command
```bash
mvn test
```

## Tech Stack
- **Language**: Java 21
- **Platform**: Spring Boot 3.4.1
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, Spring Boot Test

## Architecture and Design Decisions
- This is your typical three-layered Spring Boot app.
    - Controller layer to define the web service API and act as an entry point to the app
    - Service layer to handle business logic, transform data, etc.
    - Data layer (in this case a GitHub API client) that retrieves data
- Java 21 was chosen instead of 23 because it's LTS
- The `record` type was used for model objects in lieu of something like Lombok. This could have gone either way, but I chose to use `record` since it's part of the Java language and removes an external dependency. 
- Initially I started down the path of using WebFlux Reactive Core to show off a reactive service, then realized that was overkill. I like to keep it simple until it needs to be complicated.
- Some additional features I would have done had I had a bit more time: Performance testing, caching, open api spec generation.  