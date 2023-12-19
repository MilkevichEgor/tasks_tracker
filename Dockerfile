FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /home/app
COPY . .
RUN apk add --no-cache maven
RUN mvn install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /home/app/target app/target
EXPOSE 8080
CMD ["java", "-jar", "app/target/tasks-project-1.0-SNAPSHOT.jar"]