#
# Build stage
#
FROM maven:alpine AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline package
COPY ./src ./src
RUN mvn -B -e -C -T 1C verify

#
# Package stage
#
FROM openjdk:alpine
COPY --from=build /build/target/chinchopa.one-jar.jar /usr/local/lib/chinchopa.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/chinchopa.jar"]
