FROM maven:3.6-jdk-12-alpine as maven_build

WORKDIR /build/
COPY pom.xml /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn -Dmaven.test.skip=true package

FROM adoptopenjdk/openjdk12:alpine-jre

ARG DEPENDENCY=/build/target

ENV MESSAGE_LIMIT=$MESSAGE_LIMIT

WORKDIR /app
COPY --from=maven_build ${DEPENDENCY}/*.jar talking-players.jar
CMD java -jar talking-players.jar
