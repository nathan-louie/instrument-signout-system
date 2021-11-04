FROM openjdk:8
WORKDIR /
ADD target/finalproject-rps-enterprise-1.0-SNAPSHOT.jar app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8090
CMD java -jar -Dspring.profiles.active=prod app.jar -Dserver.port=8090