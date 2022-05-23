FROM openjdk:11
LABEL maintainer="gemuzkm@gmail.com"
COPY build/libs/*SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]




