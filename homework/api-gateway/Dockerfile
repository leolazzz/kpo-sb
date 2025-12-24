FROM eclipse-temurin:22-jre-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080 8081 8082 8083
ENTRYPOINT ["java", "-jar", "app.jar"]