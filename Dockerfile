FROM openjdk:8-jdk-alpine
ADD . /spring-app-1
WORKDIR /spring-app-1
RUN chmod +x ./mvnw
EXPOSE 8080:8080
ENTRYPOINT ./mvnw spring-boot:run