zincsearch-deployment:
	kubectl apply -f zincsearch-deployment.yml
	kubectl apply -f zincsearch-pvc.yml

zincsearch-portforward:
	# 특정 라벨을 가진 포드의 이름을 찾습니다.
	POD_NAME=$$(kubectl get pods -l app=zincsearch -o jsonpath="{.items[0].metadata.name}") && \
	# 포드 이름을 사용하여 포트 포워딩을 설정합니다.
	kubectl port-forward pod/$$POD_NAME 3000:3000

web-deployment:
	kubectl apply -f web-deployment.yml

web-ingress:
	kubectl apply -f web-ingress.yml

web-port-forward:
	# 특정 라벨을 가진 포드의 이름을 찾습니다.
	POD_NAME=$$(kubectl get pods -l app=web -o jsonpath="{.items[0].metadata.name}") && \
	# 포드 이름을 사용하여 포트 포워딩을 설정합니다.
	kubectl port-forward pod/$$POD_NAME 3000:3000
