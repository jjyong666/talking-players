FROM maven:3.6-jdk-12-alpine as maven_build

WORKDIR /build/
COPY pom.xml /build/
COPY /client/pom.xml /build/client/
COPY /server/pom.xml /build/server/
COPY /library/pom.xml /build/library/

RUN cd library && mvn -Dmaven.test.skip=true install
RUN mvn dependency:go-offline

COPY /client/src /build/client/src/
COPY /server/src /build/server/src/
COPY /library/src /build/library/src/

RUN mvn -Dmaven.test.skip=true package

FROM adoptopenjdk/openjdk12:alpine-jre

ENV MESSAGE_LIMIT=$MESSAGE_LIMIT
ENV JAR=$JAR

WORKDIR /app
COPY --from=maven_build /build/client/target/*uber.jar client.jar
COPY --from=maven_build /build/server/target/*uber.jar server.jar
CMD java -jar ${JAR}.jar
