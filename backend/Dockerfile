# jdk 이미지 설정
FROM openjdk:22-slim

# database 대기를 위한 dockerize 설치
RUN apt-get update && apt-get install -y curl
RUN curl -sSL https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz | tar -C /usr/local/bin -xz

# 컨테이너 내부 작업 디렉터리 설정
WORKDIR /app

# 빌드 파일 복사
COPY build/libs/*.jar app.jar

# 8080 포트 개방
EXPOSE 8080

# 백엔드 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

