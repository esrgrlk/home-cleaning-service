FROM public.ecr.aws/docker/library/maven:3.8-amazoncorretto-17 as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM amazoncorretto:17

COPY --from=builder /usr/src/app/target/home-cleaning-service-0.0.1-SNAPSHOT.jar /usr/app/home-cleaning-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/usr/app/home-cleaning-service-0.0.1-SNAPSHOT.jar"]