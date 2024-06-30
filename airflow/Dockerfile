FROM apache/airflow:latest-python3.9

USER root
RUN apt-get update && \
    apt-get -y install git iputils-ping curl && \
    apt-get clean

# airflow 사용자로 전환
USER airflow

# requirements.txt 파일을 Docker 이미지에 복사
COPY requirements.txt .

# pip를 최신 버전으로 업데이트
RUN pip install --upgrade pip
# requirements.txt에 명시된 파이썬 의존성 설치
RUN pip install -r requirements.txt