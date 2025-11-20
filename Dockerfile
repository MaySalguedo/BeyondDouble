FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean verify jacoco:report
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/*.jar"]