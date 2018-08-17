# Camunda - Spring


#### Camunda: [http://localhost:8080](http://localhost:8080)

**user:** admin <br>
**pass:** admin <br>


#### Springboot Data Rest is enabled, so the endpoints bellow are available
[http://localhost:8080/cities (POST|PUT|GET|DELETE)](http://localhost:8080/cities)

[http://localhost:8080/customers (POST|PUT|GET|DELETE)](http://localhost:8080/customers)

<br>

#### Some Camunda Processes are started [here](src/main/java/com/example/camunda/CamundaApplication.java).

<br>

#### Camunda bpmn files: 
[printHello.bpmn](src/main/resources/camunda/printHello.bpmn) <br>
[checkWeather.bpmn](src/main/resources/camunda/checkWeather.bpmn) <br>
[createCustomer.bpmn](src/main/resources/camunda/createCustomer.bpmn) <br>
[createCitySubProcess.bpmn](src/main/resources/camunda/createCitySubProcess.bpmn) <br>
[asyncCreateCustomer.bpmn](src/main/resources/camunda/asyncCreateCustomer.bpmn) <br> 

<br>

####Also, check the Controllers:
[CustomerController](src/main/java/com/example/camunda/controller/CustomerController.java) **Allows you to start Camunda Process to create new users Sync and Async**

```
curl -X POST \
  http://localhost:8080/async-create-customer \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: dfd0607e-7618-446f-b820-31cf0fc5eeeb' \
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
  -H 'Postman-Token: dfd0607e-7618-446f-b820-31cf0fc5eeeb' \
  -d '{
	"customerName": "Customer Name",
	"cityName": "City Name"
}'
```

[IncidentController](src/main/java/com/example/camunda/controller/IncidentController.java) **List the incidents** <br>
[JobController](src/main/java/com/example/camunda/controller/JobController.java) **Allows you to restart Jobs that fail. Check IncidentController** <br>
[ProcessController](src/main/java/com/example/camunda/controller/ProcessController.java) **Restart Process** <br>

<br>

#### Connecting to H2:

[http://localhost:8080/h2-console/login.do](http://localhost:8080/h2-console/login.do)

**url:**:  jdbc:h2:mem:testdb
**user:** sa
**pass:** <empty>
