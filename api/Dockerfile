# Dockerfile

# OpenJDK 21을 베이스 이미지로 사용합니다.
FROM openjdk:21-jdk-slim

# 기본 디렉토리를 지정합니다
WORKDIR /app
# JAR 파일을 /app 디렉토리에 복사합니다.
COPY build/libs/*.jar /app/myapp.jar
COPY .env /app/.env

# JMX 및 RMI 관련 환경 변수 설정
ENV JAVA_OPTS="-Dcom.sun.management.jmxremote \
               -Dcom.sun.management.jmxremote.port=9010 \
               -Dcom.sun.management.jmxremote.rmi.port=9010 \
               -Dcom.sun.management.jmxremote.authenticate=false \
               -Dcom.sun.management.jmxremote.ssl=false \
               -Djava.rmi.server.hostname=0.0.0.0 \
               -Dcom.sun.management.jmxremote.local.only=false \
               -Djava.rmi.server.port=1099 \
               -Xms512m -Xmx512m"

# 애플리케이션을 실행합니다.
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/myapp.jar"]

# Optional: Add a health check
# HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
#  CMD curl -f http://localhost:8080/actuator/health || exit 1