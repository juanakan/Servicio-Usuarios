FROM openjdk:17-oracle
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/users-1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]