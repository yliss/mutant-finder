# MUTANT_FINDER_SERVICE

Rest Service for search if a dna chain is mutant

REQUIREMENTS
- Java 8
- Maven
- Docker
- Docker compose

NOTE:
If you want to use docker remember to install docker in the machine that
will test the service.
Please check if maven is install in your machine, you can check it with the
following command:

```bash
mvn -v
```

You must receive a response similar to the following:

```bash
Apache Maven 3.6.3
Maven home: /usr/share/maven
...
```

## Installation

Clone this repository with the following command:

```bash
git clone https://github.com/yliss/mutant-finder.git
```

You can execute this project in local using maven or you can start 
the service with  docker-compose. Run the following command for
start docker-compose and create the containers.
```bash
docker-compose up
```

TESTING THE SERVICE

- Execute with mvn command 
  The service can run with the command 'mvn spring-boot:run'

- With an IDE
  The service can be tested with an IDE executing the class ''

## Executing with Maven

Execute the following command for start the application

```bash
mvn spring-boot:run
```

## Executing with an IDE

Execute the following java class
```bash
com.mercadolibre.services.mutantfinder.Application
```

## Executing with Docker-compose
This command will create two containers, the first one for the
mutant finder service, and the other one for the data base of 
postgres
Execute the following command for create the jar file in the 
target folder:
```bash
mvn package
```

Now execute the following command for build the containers:
```bash
docker-compose build
```

After build the containers execute one of the following commands for
start the containers:

Starting in current bash console:
```bash
docker-compose up
```

Or starting in background:
```bash
docker-compose up -d
```

## Generating the jar file

Execute the following command for create the JAR file, the jar file will be
created at the target folder
```bash
mvn package
```
For run the jar file execute the following command:
```bash
java -jar mutant-finder-service-1.0.0.jar
```

## Testing

For execute the test MAVEN is require, you can execute the test with the following command:
```bash
mvn clean compile verify
```

This will show the execution of the test, also you can check the coverage of the code opening
the jacoco report, that file is in the following path
```
target/site/jacoco/index.html
```

## At runtime
The Service will run at the port 8081, the url of the service ia the following:

http://localhost:8081/mutant-finder

Mutants endpoints
http://localhost:8081/mutant-finder/mutant

Mutants stats endpoints
http://localhost:8081/mutant-finder/mutant/stats