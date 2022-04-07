FROM openjdk:11

WORKDIR /CodeInsides

EXPOSE 8080

COPY target/code-insights-0.0.1-SNAPSHOT.jar .

CMD [ "java","-jar","code-insights-0.0.1-SNAPSHOT.jar" ]