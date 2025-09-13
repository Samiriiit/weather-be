## Use a base image with Java 11
#FROM openjdk:11-jdk
#
## Set work directory
#WORKDIR /app
#
## Copy your JAR file (replace with actual name)
#COPY target/weather-predictor-0.0.1-SNAPSHOT.jar app.jar
#
## Run the jar file
#ENTRYPOINT ["java", "-jar", "app.jar"]
#
# Step 1: Build stage
FROM maven:3.9.3-eclipse-temurin-20 AS builder
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM openjdk:17-jdk
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV REDIS_HOST=weather-redis
ENV REDIS_PORT=6379

EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
