# Camunda - Spring
 

#### Camunda: [http://localhost:8080](http://localhost:8080)

**user:** admin <br>
**pass:** admin <br>


#### Springboot Data Rest is enabled, so the endpoints below are available
[http://localhost:8080/cities (POST|PUT|GET|DELETE)](http://localhost:8080/cities)

[http://localhost:8080/customers (POST|PUT|GET|DELETE)](http://localhost:8080/customers)


#### Some Camunda Processes are started [here](src/main/java/com/example/camunda/CamundaApplication.java).


#### Camunda bpmn files: 
[printHello.bpmn](src/main/resources/camunda/printHello.bpmn) <br>
[checkWeather.bpmn](src/main/resources/camunda/checkWeather.bpmn) <br>
[createCustomer.bpmn](src/main/resources/camunda/createCustomer.bpmn) <br>
[createCitySubProcess.bpmn](src/main/resources/camunda/createCitySubProcess.bpmn) <br>
[asyncCreateCustomer.bpmn](src/main/resources/camunda/asyncCreateCustomer.bpmn) <br> 


#### Also, check the Controllers:
[CustomerController](src/main/java/com/example/camunda/controller/CustomerController.java) **Allows you to start Camunda Process to create new users Sync and Async**

```
curl -X POST \
  http://localhost:8080/async-create-customer \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"customerName": "Customer Name",
	"cityName": "City Name"
}'
```

```
curl -X POST \
  http://localhost:8080/create-customer \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"customerName": "Customer Name",
	"cityName": "City Name"
}'
```

[IncidentController](src/main/java/com/example/camunda/controller/IncidentController.java) **List the incidents** <br>

To simulate a incident, try use this curl:<br>

```
curl -X POST \
  http://localhost:8080/async-create-customer \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"customerName": "Customer1",
	"cityName": "invalid"
}'
```

Then:

```
curl -X GET \
  http://localhost:8080/incidents \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json'
```

[JobController](src/main/java/com/example/camunda/controller/JobController.java) **Allows you to restart Jobs that fail. Check IncidentController** <br>

Get the JobId and ExecutionId from [incidents](http://localhost:8080/incidents) and restart the job fixing the cityName.

```
curl -X PUT \
  http://localhost:8080/jobs/96a54d09-a232-11e8-bca8-acde48001122/retry/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
    "executionId": "d65b1ce6-a232-11e8-bca8-acde48001122",
    "variables": {
        "cityName": "new city name"
    }
}'
```

[ProcessController](src/main/java/com/example/camunda/controller/ProcessController.java) **Restart Process** <br>


#### Connecting to H2: [http://localhost:8080/h2-console/login.do](http://localhost:8080/h2-console/login.do)

**url:**:  jdbc:h2:mem:testdb<br>
**user:** sa<br>
**pass:** <empty><br>
