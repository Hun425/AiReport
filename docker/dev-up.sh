#!/bin/bash

# 개발 환경 DB 컨테이너 실행 스크립트

echo "🚀 개발 환경 데이터베이스 시작..."

# Docker Compose로 개발용 DB 컨테이너 실행
docker-compose -f docker-compose.dev.yml up -d

# 컨테이너가 준비될 때까지 대기
echo "⏳ 데이터베이스가 준비될 때까지 대기 중..."
sleep 10

# DB 연결 테스트
echo "🔍 데이터베이스 연결 테스트..."
docker-compose -f docker-compose.dev.yml exec postgres pg_isready -U postgres

if [ $? -eq 0 ]; then
    echo "✅ 데이터베이스가 성공적으로 시작되었습니다!"
    echo "📍 PostgreSQL: localhost:5432"
    echo "📍 Redis: localhost:6379"
    echo ""
    echo "🏃‍♂️ 이제 IntelliJ에서 Spring Boot 애플리케이션을 실행하세요!"
else
    echo "❌ 데이터베이스 연결에 실패했습니다."
    exit 1
fi