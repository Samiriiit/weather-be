
# Step 1: Build stage
#Uses Maven (Java build tool)
FROM maven:3.9.3-eclipse-temurin-20 AS builder
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml ./
# Required Libraries Download
RUN mvn dependency:go-offline -B

# Copy source code and build(App clean  new App  (.jar file) -DskipTests = Tests without test
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM openjdk:17-jdk-slim
# work directory created
WORKDIR /app

# Copy the built jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Optional: default profile
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port for Kubernetes service
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "app.jar"]

