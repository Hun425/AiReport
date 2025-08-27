#!/bin/bash

# 개발 환경 DB 컨테이너 종료 스크립트

echo "🛑 개발 환경 데이터베이스 종료..."

# Docker Compose로 개발용 DB 컨테이너 종료
docker-compose -f docker-compose.dev.yml down

echo "✅ 데이터베이스 컨테이너가 종료되었습니다."