FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
ENV SERVICE_PORT=8082
EXPOSE ${SERVICE_PORT}
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$SERVICE_PORT -jar /app/app.jar"]
