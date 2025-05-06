# Spring Boot app with example of Postgres JSONB data type usage

![Java CI with Maven](https://github.com/andrei-punko/spring-boot-jsonb/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Coverage](.github/badges/jacoco.svg)](https://github.com/andrei-punko/spring-boot-jsonb/actions/workflows/maven.yml)
[![Branches](.github/badges/branches.svg)](https://github.com/andrei-punko/spring-boot-jsonb/actions/workflows/maven.yml)

Includes web-server on port 9080 with `/articles` endpoint exposed.  
Supports CRUD set of operations and R operation with pagination.

## Prerequisites:

- Maven 3
- JDK 21

## Build an application:

    mvn clean install

## Build Docker image with application inside:

    docker build --no-cache ./ -t spring-boot-jsonb-app

## Start two Docker containers - with Postgres DB and application:

    docker-compose up

## Link for quick check:

http://localhost:9080/articles

## Swagger documentation

http://localhost:9080/swagger-ui/index.html


## Postman requests

Placed in appropriate [folder](postman)

## Useful CURL commands

### New article addition:

```bash
curl -i -H "Accept: application/json" -H "Content-Type: application/json" \
  -d '{ "title": "Some tittle", "text": "Some text", "author": "Pushkin", "location": { "country": "BY", "city": "Minsk" } }' \
  -X POST "http://localhost:9080/articles"
```

### Get existing article:

```bash
curl -i http://localhost:9080/articles/10
```

### Update existing article:

```bash
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{ "title": "Another tittle" }' \
  -X PATCH http://localhost:9080/articles/2
```

### Search articles by an inner field of JSONB type:

```bash
curl -i 'http://localhost:9080/articles?country=RU'
```

```bash
curl -i 'http://localhost:9080/articles?country=RU&city=Moscow'
```

### Get list of articles with pagination support:

```bash
curl -i 'http://localhost:9080/articles/all?size=2&page=4&sort=title,DESC'
```

### Deletion of article:

```bash
curl -i -X DELETE http://localhost:9080/articles/1
```

## Run functional tests:

    cd func-test
    ./gradlew clean build

Check functional test report [here](func-test/build/spock-reports/index.html)

## Video with description of the project

[//]: # ([YouTube link]&#40;https://youtu.be/AzvJe9ij53o&#41;)
[![YouTube link](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fyoutu.be%2FAzvJe9ij53o)](https://youtu.be/AzvJe9ij53o)
