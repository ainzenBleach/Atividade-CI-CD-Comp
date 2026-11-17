FROM eclipse-temurin:17-jre

WORKDIR /app

RUN useradd --system --no-create-home spring

COPY --chown=spring:spring app.jar app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]