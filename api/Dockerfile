FROM gradle:jdk11 as builder
COPY --chown=gradle:gradle .. /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
WORKDIR /opt
EXPOSE 8080
COPY build/libs/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar