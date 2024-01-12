# Tekton. Operaciones dirigidas mediante el CLI de Tekton

El CLI de Tekton ofrece ventajas sobre la operación y gestión de `Task` y `Pipeline` en Kubernetes/Openshift. Puedes encontrar el CLI en la página oficial de Tekton en la sección [Command Line Interface](https://tekton.dev/docs/cli/).

Las versiones recientes de Openshift cuentan con en su Hub con el instalador de los operadores de Tekton para su uso, en caso de que estes trabajando fuera de Openshift, el sitio oficial ofrece los medios de instalación sobre kubernetes, puedes encontrarlo en el [apartado de instalación](https://tekton.dev/docs/installation/)

## Clonar repositorio git

tkn task start git-clone \
--param=url=https://github.com/ecno20/paciente-service \
--param=deleteExisting="true" \
--workspace=name=output,claimName=shared-workspace \
--showlog

## Lista directorio

tkn task start list-directory \
--workspace=name=directory,claimName=shared-workspace \
--showlog

## Construcción código fuente

tkn task start maven \
--param=GOALS="-B,-DskipTests,clean,package" \
--workspace=name=source,claimName=shared-workspace \
--workspace=name=maven-settings,config=maven-settings \
--showlog

> Para los proyectos Java que usen el JDK 17, recomendamos hacer uso de esta imagen maven que te permitirá llevar a cabo la compilación, tendrás que proporcionar el párametro `MAVEN_IMAGE` con el siguiente valor:
`gcr.io/cloud-builders/maven:3.6.3-openjdk-17@sha256:c74c4d8f7b470c2c47ba3fcb7e33ae2ebd19c3a85fc78d7b40c8c9a03f873312`


## Creación de la imagén basada en Docker

tkn task start buildah \
--param=IMAGE="docker.io/cafaray/notificaciones:v3" \
--param=TLSVERIFY="false" \
--workspace=name=source,claimName=shared-workspace \
--serviceaccount=tekton-pipeline \
--showlog

## Despliegue en el cluster

tkn task start kubernetes-actions \
--param=script="kubectl apply -f https://raw.githubusercontent.com/brightzheng100/tekton-pipeline-example/master/manifests/deployment.yaml; kubectl get deployment;" \
--workspace=name=kubeconfig-dir,emptyDir=  \
--workspace=name=manifest-dir,emptyDir= \
--serviceaccount=tekton-pipeline \
--showlog

## Pipeline con todas las tareas

tkn pipeline start pipeline-git-clone-build-push-deploy \
-s tekton-pipeline \
--param=repo-url=https://github.com/ecno20/paciente-service \
--param=tag-name=main \
--param=image-full-path-with-tag=docker.io/ecno20/
--param=deployment-manifest=https://raw.githubusercontent.com/brightzheng100/tekton-pipeline-example/master/manifests/deployment.yaml \
--workspace=name=workspace,claimName=shared-workspace \
--workspace=name=maven-settings,config=maven-settings \
--showlog

# Notas adicionales.

## Conexión con el hub de Docker

Crear el secreto para conectar con el Docker Hub usando el archivo config.json localizado

> Dependiendo del sistema operativo que estes trabajando, puedes verificar la ruta en:
> __Windows:__   c:\Users\`{usuario}`\.docker\config.json
> __Mac:__ /User/`{usuario}`/.docker/config.json
> __Linux:__ Mac: /home/`{usuario}`/.docker/config.json

`kubectl create secret generic regcred --from-file=.dockerconfigjson=<path/to/.docker/config.json> --type=kubernetes.io/dockerconfigjson`

## Service account para la pipeline

Para poder ejecutar las pipelines de tekton que interactuan con elementos externos y requieren de credenciales especificas para la ejecución, se recomienda crear un elemento `ServiceAccount` para configurar los permisos requeridos.

`kubectl create sa tekton-pipeline `

### otorgar el rol privilegiado dentro de openshift
oc adm policy add-role-to-user edit -z tekton-pipeline

### Permisos para developers

Para los permisos de los _users_ (__rol developer__) habría que asignar el security context correspondiente

> Referencia [Security Context](https://docs.openshift.com/container-platform/4.8/cicd/pipelines/using-pods-in-a-privileged-security-context.html)

`oc adm policy add-scc-to-user privileged -z tekton-pipeline -n {user##}`  <-- deberás cambiar por tu `user##` correspondiente.
