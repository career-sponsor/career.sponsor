
certificate:
	sh certificate.sh
build:
	docker build -t nginx-minikube-proxy .
run:
	docker run -d --rm --memory="500m" --memory-reservation="256m" --cpus="0.25" --name nginx-minikube-proxy -p 443:443 -p 80:80 --network=minikube nginx-minikube-proxy
down:
	docker stop nginx-minikube-proxy
delete:
	make down
	docker image rm nginx-minikube-proxy