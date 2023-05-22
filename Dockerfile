FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pms-rest-1.0.jar
ENTRYPOINT ["java","-jar","/pms-rest-1.0.jar"]