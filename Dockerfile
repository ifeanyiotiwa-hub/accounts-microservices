
#Start with base image containing Java Runtime
FROM openjdk:11-slim as build

#Information around who maintains it
MAINTAINER betpawa.org

#Add applications jar to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar ./accounts-0.0.1-SNAPSHOT.jar

#execute the applications
ENTRYPOINT ["java", "-jar", "./accounts-0.0.1-SNAPSHOT.jar"]
