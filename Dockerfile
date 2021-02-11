FROM adoptopenjdk/maven-openjdk11:latest
VOLUME /tmp
EXPOSE 8040
ADD ./target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]