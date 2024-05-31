# Airflow


## Installation
1. build and deploy image to Docker Hub (local)
```bash
make deploy
```

## Run
```bash
make local
```

## env
create .env file
```bash
AWS_ACCESS_KEY = <AWS_ACCESS_KEY>
AWS_SECRET_KEY = <AWS_SECRET_KEY>
REGION_NAME = <REGION_NAME>
```

# kubernetes
airflow/.kube directory should be added under airflow module
it contains files below
- minikube
  - ca.crt: ca.crt in minikube in ec2 host
  - client.crt: client.crt in minikube in ec2 host
  - client.key: client.key in minikube in ec2 host

## kube_config
airflow/kube_config
```
apiVersion: v1
kind: Config
clusters:
- name: host-minikube-cluster
  cluster:
    server: http://<ec2-host>:443
    certificate-authority: minikube/ca.crt
contexts:
- name: host-minikube-context
  context:
    cluster: host-minikube-cluster
    user: host-minikube-user
users:
- name: host-minikube-user
  user:
    client-certificate: minikube/client.crt
    client-key: minikube/client.key
current-context: host-minikube-context
```