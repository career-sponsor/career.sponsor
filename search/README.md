# Search

## Elasticsearch
**Run**
```
docker-compose up -d
```



## ZincSearch (deprecated)
- https://zincsearch-docs.zinc.dev/

**Run**
1. run zincsearch-deployment.yml (local, prod)
- Makefile in project root directory
```bash
make zs-deploy
```

2. port forward (prod)
- Makefile in project root directory
- it must have --address 0.0.0.0 option for ec2 instance
```bash
make zs-pf
```

http://localhost:4080

deprecated
```bash
make start # run on local
```