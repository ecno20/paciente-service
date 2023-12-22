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


> [!Warning]
>  Don't forget to use your Hub's account to tag the image, because when pushing the image to the hub, the account is where it will be located.

`docker build -t ecno20/cloud-paciente-service:1.0 .`

The result should look like this:



  
  ```bash
Sending build context to Docker daemon  200.2kB
Step 1/7 : FROM openjdk:17-oracle
 ---> 5e28ba2b4cdb
Step 2/7 : MAINTAINER Jonathan Díaz <jdsmatemaster@gmail.com>
 ---> Running in 9eeb5bfb703c
Removing intermediate container 9eeb5bfb703c
 ---> a8f2a3052de0
Step 3/7 : EXPOSE 27017
 ---> Running in cf85673e7b2b
Removing intermediate container cf85673e7b2b
 ---> 0a8fc8773a8e
Step 4/7 : EXPOSE 8084
 ---> Running in bd7dae843657
Removing intermediate container bd7dae843657
 ---> faadf90e0d09
Step 5/7 : ARG JAR_FILE=target/*.jar
 ---> Running in 61043b489a11
Removing intermediate container 61043b489a11
 ---> a9246846d2e7
Step 6/7 : COPY target/*.jar app.jar
 ---> 939f5f1b57e2
Step 7/7 : CMD ["java", "-jar", "/app.jar"]
 ---> Running in 693a2409b50f
Removing intermediate container 693a2409b50f
 ---> c463684dddb4
Successfully built c463684dddb4
Successfully tagged ecno20/cloud-paciente-service:1.0
```



## Running the application.

Create network in `docker`

`docker network create net3`

Run the application image into a container in `docker`, use the next command:

`docker run -p 8084:8080 --network net3 cloud-paciente-service:spring-docker`

The expected output after the previous command looks like this:

<img width="928" alt="Resultado" src="https://github.com/ecno20/paciente-service/assets/144557398/302e7609-39f9-4659-b6e0-3d92b294a6be">

## Publishing

Publish the image in a docker hub account using the next command.
>[!Important]
>  If you are not logged in to the hub, use the login command:
> ```docker login {myuser}```
>  then type the password.
 
 
`docker push ecno20/cloud-paciente-service:1.0`
## Tasks & Pipelines

This project use [Tekton](https://podman.io/). as CI/CD tool. Common commands used for the automatism:

## Git clone repository
```bash
tkn task start git-clone \
--param=url=https://github.com/ecno20/paciente-service \
--param=deleteExisting="true" \
--workspace=name=output,claimName=shared-workspace \
--showlog
```
## List directory
```bash
tkn task start list-directory \
--workspace=name=directory,claimName=shared-workspace \
--showlog
```
## Build source code
```bash
tkn task start maven \
--param=GOALS="-B,-DskipTests,clean,package" \
--workspace=name=source,claimName=shared-workspace \
--workspace=name=maven-settings,config=maven-settings \
--showlog
```
><i class="fas fa-exclamation-triangle"></i></i>
>Para los proyectos Java que usen el JDK 17, recomendamos hacer uso de esta imagen maven que te permitirá llevar a cabo la compilación, tendrás que proporcionar el >párametro MAVEN_IMAGE con el siguiente valor: gcr.io/cloud-builders/kubectl@sha256:cc2e44c3355dad01d5fb017e1d1b22f1e929016360df6b311687174eb2536bed

## Build image
```bash
tkn task start buildah \
--param=IMAGE="docker.io/ecno20/cloud-paciente-service:1.0" \
--param=TLSVERIFY="false" \
--workspace=name=source,claimName=shared-workspace \
--serviceaccount=tekton-pipeline \
--showlog
```

## Deployment
```bash
tkn task start kubernetes-actions \
--param=script="kubectl apply -f https://raw.githubusercontent.com/brightzheng100/tekton-pipeline-example/master/manifests/deployment.yaml; kubectl get deployment;" \
--workspace=name=kubeconfig-dir,emptyDir=  \
--workspace=name=manifest-dir,emptyDir= \
--serviceaccount=tekton-pipeline \
--showlog
```

## Integrated pipeline
```bash
tkn pipeline start pipeline-git-clone-build-push-deploy \
-s tekton-pipeline \
--param=repo-url=https://github.com/ecno20/paciente-service \
--param=tag-name=main \
--param=image-full-path-with-tag=docker.io/cafaray/
--param=deployment-manifest=https://raw.githubusercontent.com/brightzheng100/tekton-pipeline-example/master/manifests/deployment.yaml \
--workspace=name=workspace,claimName=shared-workspace \
--workspace=name=maven-settings,config=maven-settings \
--showlog
```
For more details in the use of [Tekton](https://podman.io/) in the project, visit manifest section.

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
* [Overview of Docker Hub](https://docs.docker.com/docker-hub/)
* [Kubernetes](https://kubernetes.io/es/docs/concepts/overview/what-is-kubernetes/)
### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Docker HUB API](https://docs.docker.com/docker-hub/api/latest/)
* [API de Kubernetes](https://kubernetes.io/es/docs/concepts/overview/kubernetes-api/)
