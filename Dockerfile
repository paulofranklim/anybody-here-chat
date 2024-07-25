FROM maven:3.9.7-amazoncorretto-21 AS build
WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app
COPY src/main/resources/anybody-here-chat-specs.yaml /home/app/src/main/resources/
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:21-jdk-slim
COPY --from=build /home/app/target/anybody-here-chat.jar /home/app/anybody-here-chat.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/anybody-here-chat.jar"]