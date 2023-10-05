# paciente-service
## Proyecto
Microservicio Registro Paciente para aplicación Veterinaria.
Paciente Microservicio tiene la siguiente funcionalidad:
- Registro Paciente
## Deploy
`// TODO `
## Test
Execute the next `curl` command to validate the deploy of the service. 

```shell
curl -X 'GET' \
  'http://localhost:8084/api/pacientes/1' \
  -H 'accept: application/json'
```
The expected result should looks like:
```shell
Server response
Code 200	Details
{
  "id": "1",
  "nombre": "pelusa"
}
```
```shell
curl -X 'PUT' \
  'http://localhost:8084/api/pacientes/1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "nombre": "GATITA"
}'
}'
```
The expected result should looks like:
```shell
Server response
Code 200	OK Details
{
  "id": "1",
  "nombre": "GATITA"
}
```
```shell
curl -X 'DELETE' \
  'http://localhost:8084/api/pacientes/1' \
  -H 'accept: */*'
```
The expected result should looks like:
```shell
Server response
Code 204	No Content
```
```shell
curl -X 'GET' \
  'http://localhost:8084/api/pacientes' \
  -H 'accept: application/json'
```
The expected result should looks like:
```shell
Server response
Code 200	OK
[
  {
    "id": "6",
    "nombre": "Thor"
  },
  {
    "id": "7",
    "nombre": "Wiskas"
  },
  {
    "id": "2",
    "nombre": "Pelusa"
  },
  {
    "id": "15",
    "nombre": "Brandy"
  }
]
```
```shell
curl -X 'POST' \
  'http://localhost:8084/api/pacientes' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "15",
  "nombre": "Brandy"
}'
```
The expected result should looks like:
```shell
Server response
Code 201	OK
{
  "id": "15",
  "nombre": "Brandy"
}
 
```


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.15/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.15/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.15/reference/htmlsingle/index.html#web)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.7.15/reference/htmlsingle/index.html#data.nosql.mongodb)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
