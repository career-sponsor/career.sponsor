ZINCSEARCH_LABEL_SELECTOR := zincsearch
ZINCSEARCH_PORT := 4080

WEB_LABEL_SELECTOR := web
WEB_PORT := 3000

zincsearch-deployment:
	kubectl apply -f zincsearch-deployment.yml
	kubectl apply -f zincsearch-pvc.yml

zincsearch-portforward:
	@echo "Finding pod with label $(ZINCSEARCH_LABEL_SELECTOR)..."
	@POD_NAME=$$(kubectl get pods -l app=$(ZINCSEARCH_LABEL_SELECTOR)  -o jsonpath='{.items[0].metadata.name}'); \
	echo "Port forwarding to pod: $$POD_NAME"; \
	kubectl port-forward $$POD_NAME $(ZINCSEARCH_PORT):$(ZINCSEARCH_PORT)

web-deployment:
	kubectl apply -f web-deployment.yml

web-ingress:
	kubectl apply -f web-ingress.yml

web-port-forward:
	@echo "Finding pod with label $(WEB_LABEL_SELECTOR)..."
	@POD_NAME=$$(kubectl get pods -l app=$(WEB_LABEL_SELECTOR)  -o jsonpath='{.items[0].metadata.name}'); \
	echo "Port forwarding to pod: $$POD_NAME"; \
	kubectl port-forward $$POD_NAME $(WEB_PORT):$(WEB_PORT)
