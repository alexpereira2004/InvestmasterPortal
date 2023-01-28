#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

#Information around who maintains the image
MAINTAINER lunacom.com.br

ENV DB_SCHEMA invest_master_dev
ENV DB_USERNAME root
ENV DB_PASSWORD 123456

# Add the application's jar to the container
COPY target/Portal-0.0.1-SNAPSHOT.jar Portal-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/Portal-0.0.1-SNAPSHOT.jar"]