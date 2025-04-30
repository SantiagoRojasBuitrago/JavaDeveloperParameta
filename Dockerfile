FROM maven:3.9-amazoncorretto-17-alpine AS builder


WORKDIR /app


COPY pom.xml .
RUN mvn dependency:go-offline


COPY src ./src


RUN mvn clean install -DskipTests


FROM openjdk:17-jdk-slim


COPY --from=builder /app/target/empleado-rest-service-0.0.1-SNAPSHOT.jar /app/app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/app.jar"]