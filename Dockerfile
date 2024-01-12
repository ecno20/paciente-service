# FROM openjdk:17-jdk-alpine
FROM openjdk:17-oracle
MAINTAINER Jonathan Díaz <jdsmatemaster@gmail.com>
# a default value
ENV MONGO_HOSTNAME localhost
ENV MONGO_DB veterinaria
ENV MONGO_USER usuario_owner
ENV MONGO_PWD usuario_password
ENV TOMCAT_PORT 8084
ENV MONGO_AUTHDB admin
ENV MONGO_PORT 27017
EXPOSE 27017
EXPOSE 8084
ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]
