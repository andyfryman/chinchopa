#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /build
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline package -B
COPY ./src ./src
RUN mvn package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /build/target/chinchopa.one-jar.jar /usr/local/lib/chinchopa.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/chinchopa.jar"]
