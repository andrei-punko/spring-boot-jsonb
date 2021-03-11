
# Spring Boot application with example of Jsonb usage
![Java CI with Maven](https://github.com/andrei-punko/spring-boot-jsonb/workflows/Java%20CI%20with%20Maven/badge.svg)

Includes web-server on port 9080 with `/articles` endpoint exposed.  
Supports CRUD set of operations and R with pagination

## Prerequisities:
- JDK 11
- Maven 3

## How to build:
    mvn clean install

#### Build Docker image with application inside:
    docker build ./ -t backend-template-app

## Start application using starting script:
Use [run.bat](./run.bat) script

## Start application using set of two Docker containers - one with Postgres DB and another with application:
    docker-compose up

## Link for quick check:  
http://localhost:9080/articles

## Swagger documentation:  
http://localhost:9080/swagger-ui.html

## Useful CURL commands:
- New article addition:  
`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Some tittle", "text": "Some text", "author": { "id": 1 } }' -X POST http://localhost:9080/articles`

- Get existing article:  
`curl -i http://localhost:9080/articles/1`

- Update existing article:  
`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Another tittle" }' -X PATCH http://localhost:9080/articles/2`

- Get list of articles with pagination support:  
`curl -i 'http://localhost:9080/articles?size=2&page=4&sort=title,DESC'`

- Deletion of article:  
`curl -i -X DELETE http://localhost:9080/articles/1`
