#!/bin/bash

echo "1. copy minikube certificate"
mkdir minikube

sudo cp ~/.minikube/profiles/minikube/client.crt minikube/client.crt

sudo cp ~/.minikube/profiles/minikube/client.key minikube/client.key

echo $(ls minikube)

echo "2. create password"
# sudo yum install httpd-tools -y
htpasswd -c minikube/.htpasswd minikube
