# Use a base image with Java
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file (make sure your jar name is correct or use ARG)
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8761

# Run the jar file
# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
