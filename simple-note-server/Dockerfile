FROM openjdk:8
MAINTAINER Alexander Nicholas Salim <alexandernsalim@gmail.com>
WORKDIR /app
ADD build/libs/simple-note-server-0.0.1-SNAPSHOT.jar /app/simple-note-server-0.0.1-SNAPSHOT.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "simple-note-server-0.0.1-SNAPSHOT.jar"]
