#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline package
COPY ./src ./src
RUN mvn -B -e -C -T 1C verify

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /build/target/chinchopa.one-jar.jar /usr/local/lib/chinchopa.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/chinchopa.jar"]
