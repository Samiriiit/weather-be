# Use a base image with Java 11
# FROM openjdk:11-jdk

# # Set work directory
# WORKDIR /app

# # Copy your JAR file (replace with actual name)
# COPY target/weather-predictor-0.0.1-SNAPSHOT.jar app.jar

# # Run the jar file
# ENTRYPOINT ["java", "-jar", "app.jar"]

# FROM openjdk:11-jdk
FROM openjdk:17-jdk
WORKDIR /app
COPY target/weather-predictor-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

