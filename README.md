# Spring Boot Project with 2 microservices

The first microservice stores employee data (name, position, department, hire date, fire date). The second microservice gets data from the first one and computes aggregated data (average working time in company).

## How to run:

1. Run ```eureka-server``` project.
2. Run ```employee-service``` project (set ```dbPassword``` in environment variables or in ```application.yml``` before running).
3. Run ```aggregated-data-service``` project.

Access aggregated data from the second microservice via ```http://localhost:8002/data/```.
  Supported requests: ```GET```.

Access employee data from the first microservice via ```http://localhost:8001/employee/{id}```.
  Supported requests: ```GET, GET with id, POST, PUT, DELETE```.

Also department and position data from the first microservice can be accessed via ```http://localhost:8001/department/{id}``` and ```http://localhost:8001/position/{id}```.
  Supported requests: ```GET, GET with id, POST, PUT, DELETE```.