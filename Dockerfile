# ---- Stage 1: Build the app ----
FROM maven:3.6.3-openjdk-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy all files needed to build the app
COPY . .

# Package the application (skip tests to speed up build)
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the app in a lightweight image ----
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the app's port (adjust if needed)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
