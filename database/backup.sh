#!/bin/bash

# 환경 변수 로드 (.env 파일을 읽어오기)
source /home/ubuntu/ER_BAL/.env

# MySQL 컨테이너 이름
CONTAINER_NAME="database"

# 백업 디렉터리 (호스트 기준)
BACKUP_DIR="/home/ubuntu/ER_BAL/database/backups"
mkdir -p $BACKUP_DIR

# 백업 파일명 (날짜 형식)
BACKUP_FILE="${BACKUP_DIR}/$(date +\%F).sql"

# 컨테이너 내부에서 백업 실행
docker exec database mysqldump -uroot -p"$MYSQL_ROOT_PASSWORD" $MYSQL_DATABASE > $BACKUP_FILE

# 최근 7개 백업 파일만 유지 (오래된 파일 삭제)
ls -t $BACKUP_DIR/*.sql | tail -n +8 | xargs rm -f
