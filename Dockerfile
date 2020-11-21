FROM openjdk:11

RUN apt-get update && apt-get install -y maven
COPY . /project
RUN  cd /project && mvn package

ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://coinapp-db:27017/","-jar","/project/target/coinapp-0.0.1-SNAPSHOT.jar"]