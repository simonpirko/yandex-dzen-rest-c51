# Yandex dzen API (clone)

## Technology stack:
- Java 11
- Spring Boot 2.6.6
- Database (MySQL8 and H2)
- Spring Data JPA
- Bean Validation
- Logging SLF4j
- Gradle
- Spring Security (JWT)
- Docker
- Docker Compose
- Mapper (MupStruct)
- Swagger
- Lombok
- Flyway
- Spring Boot Test

### Board (trello.com): 

- https://trello.com/b/dJBINntF/yandex-dzen

## Docker (+ H2 database) developer version

### Run the following commands to build and run the application:

- Gradle: ./gradlew bootJar
- docker build -f Dockerfile.h2 -t tms/yandex-dzen-c51 .
- docker run -d -p 8080:8080 tms/yandex-dzen-c51
- docker ps
- port 8080
- H2 database: http://localhost:8080/db (JDBC URL: jdbc:h2:mem:db) 
- Swagger: http://localhost:8080/swagger-ui/

## Docker-compose (+ MySQL8 database) developer version

### Run the following commands to build and run the application:

- Gradle: ./gradlew bootJar
- docker-compose build
- docker-compose up -d
- docker-compose ps
- Mysql access port: 3307, Web port: 8081
- Swagger: http://localhost:8081/swagger-ui/