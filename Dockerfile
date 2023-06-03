FROM eclipse-temurin:17-alpine
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE pms-rest-1.0.0.jar
ENTRYPOINT ["java","-jar","/pms-rest-1.0.0.jar"]