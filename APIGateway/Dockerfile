# Step 1: Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file into the container
COPY target/APIGateway-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the application's port
EXPOSE 9000

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
