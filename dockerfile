FROM openjdk:17-jdk-alpine
COPY target/crud-products-0.0.1-SNAPSHOT.jar spring-app.jar
ENTRYPOINT ["java", "-jar", "spring-app.jar"]
