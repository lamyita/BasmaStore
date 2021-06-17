



FROM openjdk:8
RUN mkdir /con

EXPOSE 8086
ADD target/basma_Strore-0.0.1-SNAPSHOT.jar con/dockerjava.jar



ENTRYPOINT ["java", "-jar", "con/dockerjava.jar"]