FROM openjdk:17-jdk-alpine
COPY target/demo.jar demo.jar
ENTRYPOINT ["java","-jar","/demo.jar"]