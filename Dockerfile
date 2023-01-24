FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
WORKDIR /opt/app
# cp target/football-league-standings.jar /opt/app/football-league-standings.jar
COPY ${JAR_FILE} football-league-standings.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","football-league-standings.jar"]