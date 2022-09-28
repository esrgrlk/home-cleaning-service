FROM openjdk:18
ADD target/home-cleaning-service-0.0.1-SNAPSHOT.jar home-cleaning-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "home-cleaning-service-0.0.1-SNAPSHOT.jar"]