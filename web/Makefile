IMAGE_NAME=bgpark82/company-visa-web
TAG=latest

deploy: build tag push

build:
	docker build -t $(IMAGE_NAME):$(TAG) .

tag:
	docker tag $(IMAGE_NAME):$(TAG) $(IMAGE_NAME):$(TAG)

push:
	docker push $(IMAGE_NAME):$(TAG)

inspect:
	docker image inspect $(IMAGE_NAME):$(TAG)

run:
	docker run -p 3000:3000 $(APP_NAME)

local:
	rails server