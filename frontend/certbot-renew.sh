#!/bin/bash

# Certbot 갱신 시도
certbot renew --nginx --quiet

# Nginx 재시작 (인증서 적용)
nginx -s reload

