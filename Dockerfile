FROM maven:3.9.8-amazoncorretto-17-al2023 AS build

WORKDIR /app

COPY /target/*.jar app.jar

ENV TZ=Europe/Moscow

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]