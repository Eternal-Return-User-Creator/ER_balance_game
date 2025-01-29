#!/bin/bash

# Certbot 갱신 시도
sudo certbot renew --nginx --quiet

# Nginx 재시작 (인증서 적용)
docker exec frontend nginx -s reload

