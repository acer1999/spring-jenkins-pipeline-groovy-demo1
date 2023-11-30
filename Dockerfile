FROM eclipse-temurin:17
RUN mkdir -p /app
WORKDIR /app
VOLUME [ "/tmp" ]
COPY target/*.jar app.jar
ENTRYPOINT [ "java","-jar","/app/app.jar"]
