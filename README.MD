
# Spring Boot application with example of PostgreSQL's JSONB data type usage

![Java CI with Maven](https://github.com/andrei-punko/spring-boot-jsonb/workflows/Java%20CI%20with%20Maven/badge.svg)

Includes web-server on port 9080 with `/articles` endpoint exposed.  
Supports CRUD set of operations and R operation with pagination.

## Prerequisites:
- JDK 11
- Maven 3

## Build an application:
    mvn clean install

## Build Docker image with application inside:
    docker build ./ -t spring-boot-jsonb-app

## Start two Docker containers - with Postgres DB and application:
    docker-compose up

## Link for quick check:  
http://localhost:9080/articles

## Swagger documentation:  
http://localhost:9080/swagger-ui.html

## Useful CURL commands

### New article addition:
    curl -X POST "http://localhost:9080/articles" -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Some tittle", "text": "Some text", "author": "Pushkin", "location": { "country": "BY", "city": "Minsk" } }'

### Get existing article:
    curl -i http://localhost:9080/articles/1

### Update existing article:
    curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Another tittle" }' -X PATCH http://localhost:9080/articles/2

### Search articles by an inner field of JSONB type:
    curl -i 'http://localhost:9080/articles?country=BY'
    curl -i 'http://localhost:9080/articles?country=BY&city=Minsk'

### Get list of articles with pagination support:  
    curl -i 'http://localhost:9080/articles/all?size=2&page=4&sort=title,DESC'

### Deletion of article:  
    curl -i -X DELETE http://localhost:9080/articles/1
