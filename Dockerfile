FROM openjdk:17-jdk-alpine

EXPOSE 8080

ADD target/CarManager-0.0.1-SNAPSHOT.jar car_manager.jar

CMD ["java", "-jar", "car_manager.jar"]