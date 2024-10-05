# Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM amazoncorretto:17

EXPOSE 8080

COPY --from=build /target/springlogin.jar springlogin.jar

ENTRYPOINT ["java", "-jar", "springlogin.jar"]
