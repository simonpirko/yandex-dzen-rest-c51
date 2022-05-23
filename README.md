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

### Board (trello.com): 

- https://trello.com/b/dJBINntF/yandex-dzen

## Docker (+ H2 database)

### Run the following commands to build and run the application:

- Gradle - Task - build - bootJar
- docker build -f Dockerfile.h2 -t tms/yandex-dzen-c51 .
- docker run -d -p 8080:8080 tms/yandex-dzen-c51
- docker ps
- port 8080

## Docker-compose (+ MySQL8 database)

### Run the following commands to build and run the application:

- Gradle - Task - build - bootJar
- docker-compose build
- docker-compose up -d
- docker-compose ps
- Mysql access port: 3307, Web port: 8081 