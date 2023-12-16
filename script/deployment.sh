#!/bin/bash

# if [[ $# -ne 2 ]]; then
#     echo 'Too many/few arguments, expecting two' >&2
#     echo 'ex. deployment.sh my-nginx docker.io/nginx:latest' >&2
#     exit 1
# fi
DEPLOYMENT_NAME="paciente-service"
IMAGE_FULL_NAME="docker.io/ecno20/cloud-paciente-service:1.0"

echo "Working with values: DEPLOYMENT_NAME = $DEPLOYMENT_NAME & IMAGE = $IMAGE_FULL_NAME"
echo "starting deployment"
kubectl delete deployment $DEPLOYMENT_NAME
kubectl create deployment $DEPLOYMENT_NAME --replicas=2 --image=$IMAGE_IMAGE_FULL_NAME
echo "verifying deployment"
CURRENT_ALREADY_PODS=$(kubectl get deployments $DEPLOYMENT_NAME | awk 'NR==2{print $4}')
MINIMAL_AVAILABLE_PODS=2
COUNTER=1
while [ $((CURRENT_ALREADY_PODS)) -lt $((MINIMAL_AVAILABLE_PODS)) ]
  do
    echo "waiting for pods"
    CURRENT_ALREADY_PODS=$(kubectl get deployments $DEPLOYMENT_NAME | awk 'NR==2{print $4}')
    COUNTER=$(( $COUNTER + 1 ))
    if [ $COUNTER -gt 20 ]
    then
      echo "It looks something went wrong with the deployment $DEPLOYMENT_NAME. I can not wait more ..."
      exit 1
    else
      sleep 3
    fi
done;
echo "deployment ready, here the status:"
kubectl get deployment $DEPLOYMENT_NAME -o wide
