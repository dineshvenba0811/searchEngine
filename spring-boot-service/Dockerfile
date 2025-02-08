# Use a Debian-based JDK 21 image for compatibility
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Gradle Wrapper files
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Grant execution permissions to Gradle Wrapper

RUN chmod +x gradlew

# Build the Spring Boot app
RUN ./gradlew clean build -x test

# Use the same runtime JDK for consistency
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "app.jar"]