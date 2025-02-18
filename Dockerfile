# Use the official Maven image to build the project
FROM maven:3.8.7-openjdk-18-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download project dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy project files and build the application
COPY src ./src
RUN mvn package -DskipTests

# Use a slim Java image to run the project
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built artifact from the build stage. Adjust the jar name if needed.
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
