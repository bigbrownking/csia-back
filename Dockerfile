FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

ARG CACHE_DIR=/root/.m2
VOLUME ${CACHE_DIR}

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src /app/src
COPY src/main/resources /app/resources

RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/agrohack-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]



COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src /app/src
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/agrohack-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
