FROM adoptopenjdk/openjdk11:latest

ADD target/assembleia.jar assembleia.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "assembleia.jar"]