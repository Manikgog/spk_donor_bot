# Stage 1: Build the application
FROM maven:3.8.7-eclipse-temurin-17 AS builder
LABEL authors="M.Gogolin"
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn package -DskipTests

# Stage 2: Extract layers
FROM eclipse-temurin:17-jre-alpine as layers
WORKDIR /tmp
ARG JAR_FILE=/build/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar
WORKDIR /workspace
RUN java -Djarmode=layertools -jar /tmp/app.jar extract

# Stage 3: Create the final image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /workspace
COPY --from=layers /workspace/dependencies/ ./
COPY --from=layers /workspace/snapshot-dependencies/ ./
COPY --from=layers /workspace/spring-boot-loader/ ./
COPY --from=layers /workspace/application/ ./
COPY dataFiles /workspace/dataFiles
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]