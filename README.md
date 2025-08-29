# 🚀 AI 알고리즘 학습 로드맵 서비스

solved.ac API를 활용한 개인화된 학습 로드맵 및 AI 코드 리뷰 서비스

## 📋 프로젝트 개요

- **목표**: solved.ac 데이터 분석을 통한 기업별 맞춤 학습 로드맵 제공
- **기술 스택**: Kotlin, Spring Boot 3.x, PostgreSQL, React (Next.js)
- **아키텍처**: Clean Architecture + 멀티모듈 구조

## 🛠️ 개발 환경 설정

### 1. 필수 요구사항
- JDK 21 이상
- Docker & Docker Compose
- Git

### 2. Docker를 이용한 개발환경 설정 (권장)

#### Windows 사용자
```bash
# 개발용 데이터베이스 시작
.\docker\dev-up.bat

# 개발용 데이터베이스 종료
.\docker\dev-down.bat
```

#### macOS/Linux 사용자
```bash
# 개발용 데이터베이스 시작
chmod +x ./docker/dev-up.sh
./docker/dev-up.sh

# 개발용 데이터베이스 종료
./docker/dev-down.sh
```

### 3. 애플리케이션 실행

1. **데이터베이스 시작**: 위의 Docker 스크립트 실행
2. **애플리케이션 실행**: IntelliJ에서 `AlgoroadmapApplication.kt` 실행
3. **접속 확인**: http://localhost:8080/api/v1/health

### 4. 전체 환경 Docker로 실행 (선택사항)

```bash
# 전체 애플리케이션 + DB 실행
docker-compose up --build

# 백그라운드 실행
docker-compose up -d --build

# 종료
docker-compose down
```

## 🗂️ 프로젝트 구조

```
algoroadmap-service/
├── domain/              # 비즈니스 로직 (순수 Kotlin)
├── application/         # 애플리케이션 서비스 (유스케이스)
├── infrastructure/      # 외부 의존성 (DB, API 클라이언트)
├── presentation/        # 컨트롤러 계층 (REST API)
├── docker/              # Docker 관련 스크립트
└── markdown/            # 프로젝트 문서
```

## 🔌 API 엔드포인트

### 인증
- `GET /api/v1/auth/google` - **Google OAuth 시작**
- `GET /api/v1/auth/google/callback` - **OAuth 콜백 처리**
- `POST /api/v1/auth/logout` - 로그아웃

### 사용자
- `GET /api/v1/users/me` - 내 정보 조회
- `PUT /api/v1/users/me/handle` - **solved.ac 핸들 등록/수정**

### 데이터 동기화
- `POST /api/v1/users/me/sync` - solved.ac 데이터 동기화 요청
- `GET /api/v1/users/me/sync/status` - 동기화 상태 조회

### 대시보드
- `GET /api/v1/dashboard/me` - 대시보드 데이터 조회

### 헬스체크
- `GET /api/v1/health` - API 상태 확인

## 🐳 Docker 구성

### 개발 환경 (권장)
- **PostgreSQL**: localhost:5432
- **Redis**: localhost:6379
- **애플리케이션**: IntelliJ에서 직접 실행

### 프로덕션 환경
- 모든 서비스가 Docker 컨테이너로 실행
- 멀티스테이지 빌드로 최적화
- Health Check 포함

## 📚 관련 문서

- [기획안](./markdown/기획안.md)
- [개발 가이드라인](./markdown/개발가이드라인.md) 
- [API 명세](./markdown/API명세.md)
- [와이어프레임](./markdown/와이어프레임.md)
- [개발 진행 계획](./개발진행계획.md)

## 🤝 개발 가이드

1. **Clean Architecture 준수**: 의존성 방향이 올바른지 확인
2. **테스트 작성**: 핵심 비즈니스 로직에 대한 단위 테스트
3. **커밋 메시지**: feat/fix/docs 등의 prefix 사용
4. **코드 리뷰**: PR 생성 시 리뷰 요청

---

**마지막 업데이트**: 2025-08-29 (**Google OAuth 인증 시스템 적용**)  
**개발자**: 개발팀

## 🎯 **최신 변경사항 (v1.1)**

### ✨ **주요 기능 추가**
- **Google OAuth 2.0 로그인** 지원
- **solved.ac 핸들 수동 등록** 시스템
- **SpringDoc OpenAPI 2.8.0** 업그레이드

### 🔄 **인증 방식 변경**
1. **이전**: solved.ac 직접 OAuth (API 미지원)
2. **현재**: Google OAuth → solved.ac 핸들 등록 → 데이터 동기화

### 🚀 **사용자 플로우**
1. Google 계정으로 로그인
2. solved.ac 핸들 등록
3. 자동 데이터 동기화
4. 개인화된 대시보드 확인