# 노드 이미지 선택
FROM node:23-alpine as build

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 시 사용할 ARG 정의
ARG VITE_BACKEND_URL

# 환경 변수 설정
ENV VITE_BACKEND_URL=${VITE_BACKEND_URL}

# package.json, lock 파일 복사
COPY package*.json ./

# 패키지 설치
RUN npm install

# 소스 코드 복사
COPY . .

# 빌드
RUN npm run build

# nginx 이미지 선택
FROM nginx:latest

# certbot 설치
RUN apt update && apt install -y certbot python3-certbot-nginx

# 인증서 갱신 스크립트 복사
COPY certbot-renew.sh /usr/local/bin/certbot-renew.sh
RUN chmod +x /usr/local/bin/certbot-renew.sh

# 빌드된 파일들 nginx 디렉터리로 이동
COPY --from=build /app/dist /usr/share/nginx/html

# HTTP 포트 열기
EXPOSE 80

# nginx 실행
ENTRYPOINT ["nginx", "-g", "daemon off;"]

