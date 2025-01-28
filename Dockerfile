FROM openjdk:17-jdk-alpine

WORKDIR /app

EXPOSE 8081

COPY target/*.jar operation-service.jar

ENTRYPOINT ["java", "-jar", "operation-service.jar"]