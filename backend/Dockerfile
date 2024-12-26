# Base image
FROM openjdk:22-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the build files
COPY build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

