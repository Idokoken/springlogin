# Build stage
FROM maven:3.8.5-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
COPY . .
RUN mvn clean package -DskipTests

# Run stage
#Use an official Java runtime as a parent image
FROM amazoncorretto:17

LABEL version="1.0"

# Expose the port the Spring Boot application runs on
EXPOSE 8080:8080

#Set the working directory inside the container
WORKDIR /app

#Copy the application JAR file to the container
#COPY target/springlogin.jar /app/springlogin.jar
COPY --from=build /app/target/springlogin.jar springlogin.jar

#Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "springlogin.jar"]
