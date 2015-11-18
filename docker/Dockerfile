FROM anapsix/alpine-java:jre8

MAINTAINER "Georgios Andrianakis"

RUN mkdir /app

WORKDIR /app

COPY docker-events.jar /app/

CMD java -jar docker-events.jar
