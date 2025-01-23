# 1. Gradle 이미지 사용 (Gradle을 포함한 이미지)
FROM gradle:jdk22 as builder

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle Wrapper 및 빌드 파일 복사 (프로젝트 루트의 모든 파일을 복사)
COPY . .

# 4. Gradle 빌드 실행
RUN gradle build -x test --no-daemon

# 5. 최종 이미지 생성
FROM openjdk:22-slim

# 6. 컨테이너 작업 디렉토리 설정
WORKDIR /app

# 7. Gradle 빌드에서 생성된 .jar 파일을 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 8. 8080 포트 개방
EXPOSE 8080

# 9. 백엔드 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

