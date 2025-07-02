# Stage 1: Build the application using Gradle
FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle build -x test

# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/ExpenseTracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
