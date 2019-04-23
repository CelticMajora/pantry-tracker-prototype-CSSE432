FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/pantry-tracker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]