# Build image based on openjdk 8
FROM openjdk:8-alpine

# Add executable jar file
ADD target/app-1.0.jar app.jar

# Run command to start application
ENTRYPOINT ["java", "-jar", "app.jar"]