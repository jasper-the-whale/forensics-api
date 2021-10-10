FROM openjdk:11
ADD build/libs/forensics-api.jar forensics-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "forensics-api.jar"]