FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app.jar"]