# Multi-stage Dockerfile for BeyondDouble Java project
# Stage 1: Build and Test
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY . .

# Make gradlew executable
RUN chmod +x ./gradlew

# Run tests, generate coverage reports, and build the runnable JAR
RUN ./gradlew build jacocoTestReport

# Stage 2: Runtime image with artifacts
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/build/libs/*.jar /app/BeyondDouble.jar

# Copy test coverage reports and build artifacts
COPY --from=builder /app/build/reports/jacoco /app/coverage-reports
COPY --from=builder /app/build/jacoco/test.exec /app/jacoco.exec

# Expose port if the app needs it (optional)
EXPOSE 8080

# Health check for the running application
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -version || exit 1

# Default command to run the application
CMD ["java", "-jar", "/app/BeyondDouble.jar"]
