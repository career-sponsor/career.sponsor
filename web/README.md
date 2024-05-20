# Web

## Installation 
1. build and deploy image to Docker Hub (local, prod)
- Makefile in web directory
```bash
make deploy
```

## Run
1. run web-deployment.yml (local, prod)
- Makefile in project root directory
```bash
make web-deploy
```

2. port forward (prod)
open screen session to run portforward on background
```bash
screen -S portforward 
```
- Makefile in project root directory
- it must have --address 0.0.0.0 option for ec2 instance 
```bash
make web-pf
```

deprecated
```bash
make run # docker
make local # local 
```