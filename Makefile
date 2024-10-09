SHELL := /bin/zsh
JAVA_HOME := $(/usr/libexec/java_home -v 21)

ELASTICSEARCH_LABEL_SELECTOR := elasticsearch
ELASTICSEARCH_PORT := 9200

WEB_LABEL_SELECTOR := web
WEB_PORT := 3000

start: web-deploy es-deploy web-pf

build:
	cd api && ./gradlew clean build

test: build
	docker-comopse -f docker-compose-local.yml up --build

deploy: build
	docker-compose -f docker-compose-prod.yml up --build

# elasticsearch deploy
es-deploy:
	kubectl apply -f es-deployment.yml
	kubectl apply -f es-pvc.yml

# elasticsearch portforward
es-pf:
	@echo "Finding pod with label $(ZINCSEARCH_LABEL_SELECTOR)..."
	@POD_NAME=$$(kubectl get pods -l app=$(ZINCSEARCH_LABEL_SELECTOR)  -o jsonpath='{.items[0].metadata.name}'); \
	echo "Port forwarding to pod: $$POD_NAME"; \
	kubectl port-forward $$POD_NAME --address 0.0.0.0 $(ZINCSEARCH_PORT):$(ZINCSEARCH_PORT)

# web deploy
web-deploy:
	kubectl apply -f web-deployment.yml

# web portforward
web-pf:
	@echo "Finding pod with label $(WEB_LABEL_SELECTOR)..."
	@POD_NAME=$$(kubectl get pods -l app=$(WEB_LABEL_SELECTOR)  -o jsonpath='{.items[0].metadata.name}'); \
	echo "Port forwarding to pod: $$POD_NAME"; \
	kubectl port-forward $$POD_NAME --address 0.0.0.0 $(WEB_PORT):$(WEB_PORT)

# web ingress
web-ingress:
	kubectl apply -f web-ingress.yml
