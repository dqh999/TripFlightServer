FROM gradle:8.11.1-jdk23 AS build
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon -x test

FROM openjdk:23-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

# docker build -t airline-server:1.0.0 -f ./Dockerfile .
#docker login
#docker create dqh999/airline-server:1.0.0 repository on DockerHub
#docker tag airline-server:1.0.0 dqh999/airline-server:1.0.0
#docker push dqh999/airline-server:1.0.0
