# paciente-service
## Proyecto
Implementation of paciente microservice for the vet application. The paciente microservice provides the following functionalities:
- Registro Paciente

The following scripts are provided for the MongoDB database/collections creation:

- veterinariadb_creation.js
- paciente_collection.js
- load_collections_data.js

## Deploy
## Creating the image
This image is based on [linux/arm64](https://hub.docker.com/_/openjdk/tags?page=1&name=17) for Linux.

The complete specification of the image that contains the application is in the [Dockerfile](Dockerfile)
## Building the image.
Build the image using `docker` or `podman`, below the commands for using podman. More information on how to use it [here](https://podman.io/). The first version for a standard is frequently used `1.0.`

> <i class="fas fa-exclamation-triangle"></i>
> **Warning:**
>  Don't forget to use your Hub's account to tag the image, because when pushing the image to the hub, the account is where it will be located.

`docker build -t cloud-paciente-service:spring-docker .`

`docket tag cloud-paciente-service:spring-docker ecno20/cloud-paciente-service:v1.0`

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
