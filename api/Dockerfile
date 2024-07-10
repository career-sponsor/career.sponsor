# Dockerfile

# OpenJDK 21을 베이스 이미지로 사용합니다.
FROM openjdk:21-jdk-slim

# JAR 파일을 /app 디렉토리에 복사합니다.
COPY build/libs/*.jar /app/myapp.jar

# 애플리케이션을 실행합니다.
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]