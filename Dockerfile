# 멀티스테이지 빌드로 최적화
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Gradle 의존성 캐싱을 위해 build files 먼저 복사
COPY build.gradle.kts settings.gradle.kts ./
COPY domain/build.gradle.kts ./domain/
COPY application/build.gradle.kts ./application/
COPY infrastructure/build.gradle.kts ./infrastructure/
COPY presentation/build.gradle.kts ./presentation/

# 의존성 다운로드 (캐싱 활용)
RUN gradle dependencies --no-daemon

# 소스 코드 복사
COPY . .

# 애플리케이션 빌드
RUN gradle build -x test --no-daemon

# 실행 단계
FROM openjdk:21-jre-slim

# 타임존 설정
RUN apt-get update && apt-get install -y tzdata && \
    ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/presentation/build/libs/*.jar app.jar

# JVM 최적화 옵션
ENV JAVA_OPTS="-Xms512m -Xmx1g -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:+UseStringDeduplication"

# 포트 노출
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/v1/health || exit 1

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]