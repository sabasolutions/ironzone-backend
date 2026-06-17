# Multi-stage Dockerfile for building and running the Spring Boot backend
# Stage 1: build with Maven
FROM maven:3.8.8-jdk-17 AS build
WORKDIR /workspace

# Copy only the files needed for a Maven build to improve caching
COPY pom.xml ./
COPY src ./src

# Build the project (skip tests for faster build; remove -DskipTests to run tests)
RUN mvn -B -DskipTests package

# Stage 2: runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the fat jar produced by the build stage. Uses wildcard to match versioned jar.
COPY --from=build /workspace/target/*.jar app.jar

# Expose the default Spring Boot port (change if your application uses a different port)
EXPOSE 8080

# Use a reasonable default JVM option and run the jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

