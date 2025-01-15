# jdk 이미지 설정
FROM openjdk:22-slim

# 컨테이너 내부 작업 디렉터리 설정
WORKDIR /app

# 백엔드 파일 복사
COPY . .

# Gradle 빌드
RUN ./gradlew build -x test --no-daemon

RUN ls -l

# 빌드 파일 복사
COPY build/libs/*.jar app.jar

# 8080 포트 개방
EXPOSE 8080

# 백엔드 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

