FROM openjdk:21-jdk-alpine
VOLUME /tmp
EXPOSE 9080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/spring-boot-jsonb-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/app.jar"]
