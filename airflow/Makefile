IMAGE_NAME=bgpark82/uk-company-airflow
TAG=latest

.PHONY: build tag push

docker push:
	makefile build tag push

build:
	docker build -t $(IMAGE_NAME):$(TAG) .

tag:
	docker tag $(IMAGE_NAME):$(TAG) $(IMAGE_NAME):$(TAG)

push:
	docker push $(IMAGE_NAME):$(TAG)