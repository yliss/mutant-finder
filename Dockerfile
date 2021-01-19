FROM java:8

WORKDIR /

ADD ./target/mutant-finder-service.jar /mutant-finder-service.jar

EXPOSE 8088

CMD java -Dserver.port=8081 -Dspring.datasource.url=$DB1_URL -jar mutant-finder-service.jar