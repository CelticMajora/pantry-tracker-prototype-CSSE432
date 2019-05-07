FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /build/libs/pantry-tracker-0.1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]