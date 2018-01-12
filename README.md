# Axon and Spring Boot Devtools

Axon 3.1.2 does not work with `spring-boot-devtools:1.5.9.RELEASE`.

## Running the example

### Failure scenario 💩

With the `spring-boot-devtools` dependency added, run the app with:

```console
$ ./mvnw spring-boot:run
```

Then execute the following requests:

```console
$ curl -X POST \
    http://localhost:8080/things \
    -H 'Content-Type: application/json' \
    -d '{
          "id": "1",
          "name": "Test"
  }'
1
  
$ curl -X PUT \
    http://localhost:8080/things/1 \
    -H 'Content-Type: application/json' \
    -d '{
  	"name": "Test 2"
  }'
{"timestamp":1515786529395,"status":500,"error":"Internal Server Error","exception":"org.axonframework.eventsourcing.IncompatibleAggregateException","message":"Aggregate identifier must be non-null after applying an event. Make sure the aggregate identifier is initialized at the latest when handling the creation event.","path":"/things/1"}  
```

and you'll see the error:

`org.axonframework.eventsourcing.IncompatibleAggregateException","message":"Aggregate identifier must be non-null after applying an event. ...`

### Successful scenario ✅

Now to "fix" the problem, comment out the `spring-boot-devtools` dependency:

```xml
...
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- see https://github.com/AxonFramework/AxonFramework/issues/282 -->
        <!-- comment the spring-boot-devtools dependency out for the app to work on the PUT -->
        <!--<dependency>-->
            <!--<groupId>org.springframework.boot</groupId>-->
            <!--<artifactId>spring-boot-devtools</artifactId>-->
            <!--<optional>true</optional>-->
        <!--</dependency>-->
        <dependency>
            <groupId>org.axonframework</groupId>
            <artifactId>axon-spring-boot-starter</artifactId>
            <version>${axon.version}</version>
        </dependency>
...        
```

and run the app and requests as before:

```console
$ ./mvnw clean spring-boot:run
...

$ curl -X POST \
    http://localhost:8080/things \
    -H 'Content-Type: application/json' \
    -d '{
          "id": "1",
          "name": "Test"
  }'
1
  
$ curl -X PUT \
    http://localhost:8080/things/1 \
    -H 'Content-Type: application/json' \
    -d '{
  	"name": "Test 2"
  }'
  
$  
```

no error and note that in the logs you'll see the event handler pick up the event:

```
2018-01-12 21:51:59.027  INFO 61931 --- [nio-8080-exec-2] com.example.ThingHandler                 : Thing changed: Test 2
```

## Note

Running this application from the packaged Jar 
(with `./mvnw package && java -jar target/axon-spring-boot-devtools-isssue-1.0-SNAPSHOT.jar`) 
works, even despite having `spring-boot-devtools` on the classpath. 

Therefore the error occurs only when running in "development mode".
