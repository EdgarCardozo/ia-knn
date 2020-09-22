FROM gradle:jdk11 as builder
WORKDIR /code
COPY . /code/
RUN gradle build

FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
EXPOSE 8080
WORKDIR /app
COPY --from=builder /code/build/libs/*.jar .
CMD java $JAVA_OPTS -jar *.jar
