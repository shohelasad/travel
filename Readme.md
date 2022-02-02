# TRAVEL - docker based proejct


## Prerequisites

* Docker 19.03.x
* Docker Compose 1.25.x

## Used Technologies

* Spring Boot 2.3.4
* Postgresql
* Spring Boot Validation
* Spring Boot Jpa
* Spring Boot Actuator
* Lombok
* Dev Tools
* Open Api 3


## How to run

1. Package the application as a JAR file with skipping the tests.

```sh
./mvnw clean package -DskipTests
```

2. Run the Spring Boot application and PostgreSQL with Docker Compose:

```sh
docker-compose up -d
```

## Test API with Swagger 
http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/

## Note
* Database PostgresSQL is configured for docker container
* We could use Liquibase for manange change. We add Changed.md for managing change and migration in database and configuration
* For production run, create database table following Change.md and set spring.jpa.hibernate.ddl-auto=none(You can set spring.jpa.hibernate.ddl-auto=create to run without creating table manually) 
* Sample Unit tests are implemented for controller, service and repository
* Config server and micro-services, api gateway and load balancing, caching is not covered for time limitation



