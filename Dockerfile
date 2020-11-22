FROM openjdk:11

RUN apt-get update && apt-get install -y maven
COPY . /project
RUN  cd /project && mvn package

ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI}", "-jar","/project/application/target/coinapp.application-0.0.1-SNAPSHOT.jar"]