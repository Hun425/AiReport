# Google OAuth 테스트 환경 구성 가이드

## 1. Google OAuth 앱 등록

### 1.1 Google Cloud Console 설정
1. [Google Cloud Console](https://console.cloud.google.com) 접속
2. 새 프로젝트 생성 또는 기존 프로젝트 선택
3. **API 및 서비스** → **사용자 인증 정보**로 이동

### 1.2 OAuth 2.0 클라이언트 ID 생성
1. **사용자 인증 정보 만들기** → **OAuth 클라이언트 ID** 선택
2. **애플리케이션 유형**: 웹 애플리케이션
3. **이름**: `AI 알고리즘 학습 로드맵 서비스`
4. **승인된 리디렉션 URI**: 
   - `http://localhost:8080/api/v1/auth/google/callback`
   - `http://localhost:8080/login/oauth2/code/google` (Spring Security 기본)

### 1.3 클라이언트 정보 획득
생성 완료 후 다음 정보를 획득합니다:
- **Client ID**: `your_google_client_id.apps.googleusercontent.com`
- **Client Secret**: `your_google_client_secret`

### 1.4 OAuth 동의 화면 설정
1. **OAuth 동의 화면** 메뉴로 이동
2. **외부** 사용자 유형 선택 (테스트용)
3. **앱 정보** 입력:
   - **앱 이름**: AI 알고리즘 학습 로드맵 서비스
   - **사용자 지원 이메일**: 개발자 이메일
   - **승인된 도메인**: `localhost`

## 2. 환경 변수 설정

### 2.1 로컬 개발 환경
`.env` 파일 생성 또는 IDE 환경 변수 설정:

```bash
# Google OAuth 설정
GOOGLE_CLIENT_ID=your_google_client_id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your_google_client_secret

# JWT 설정  
JWT_SECRET=your-jwt-secret-key-at-least-256-bits-long
JWT_EXPIRATION=3600000

# 데이터베이스 설정
DATABASE_URL=jdbc:postgresql://localhost:5432/algoroadmap
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password

# 로깅 레벨
LOG_LEVEL=DEBUG
```

### 2.2 Docker 환경
`docker-compose.dev.yml`에서 환경 변수 설정:

```yaml
environment:
  - GOOGLE_CLIENT_ID=your_google_client_id.apps.googleusercontent.com
  - GOOGLE_CLIENT_SECRET=your_google_client_secret
  - JWT_SECRET=your-jwt-secret-key
  - JWT_EXPIRATION=3600000
```

## 3. OAuth 플로우 테스트

### 3.1 로컬 서버 실행
```bash
# Docker로 실행
docker-compose -f docker-compose.dev.yml up

# 또는 IDE에서 직접 실행
./gradlew :presentation:bootRun
```

### 3.2 OAuth 인증 테스트 단계

#### Step 1: 인증 시작
브라우저에서 접속:
```
http://localhost:8080/api/v1/auth/google
```

#### Step 2: Google 인증 페이지 확인
- Google 로그인 페이지로 리디렉션 확인
- 구글 계정 선택 및 로그인
- 앱 권한 승인 화면 확인 (프로필, 이메일 정보 접근)

#### Step 3: 콜백 처리 확인
- 콜백 URL로 리디렉션 확인
- JWT 토큰이 쿠키에 설정되었는지 확인
- `/onboarding` 페이지로 리디렉션 확인

#### Step 4: 인증된 API 호출 테스트
```bash
# solved.ac 핸들 등록
curl -X PUT http://localhost:8080/api/v1/users/me/handle \
  -H "Cookie: accessToken=your_jwt_token" \
  -H "Content-Type: application/json" \
  -d '{"handle": "your_solvedac_handle"}'

# 사용자 정보 조회
curl -X GET http://localhost:8080/api/v1/users/me \
  -H "Cookie: accessToken=your_jwt_token"

# 동기화 요청 (핸들 등록 후)
curl -X POST http://localhost:8080/api/v1/users/me/sync \
  -H "Cookie: accessToken=your_jwt_token"
```

## 4. 테스트 케이스

### 4.1 성공 케이스
- [ ] Google OAuth 인증 URL 생성 확인
- [ ] Google 인증 페이지 리디렉션 확인
- [ ] 정상적인 인증 코드로 JWT 토큰 생성 확인
- [ ] JWT 토큰으로 보호된 API 접근 확인
- [ ] solved.ac 핸들 등록/검증 정상 동작 확인
- [ ] 사용자 데이터 동기화 정상 동작 확인

### 4.2 실패 케이스
- [ ] 잘못된 인증 코드 처리 확인
- [ ] 만료된 JWT 토큰 처리 확인
- [ ] 인증되지 않은 API 접근 차단 확인
- [ ] 유효하지 않은 solved.ac 핸들 처리 확인
- [ ] solved.ac API 오류 처리 확인

## 5. 로그 확인 포인트

### 5.1 인증 관련 로그
```
INFO  - Google OAuth 인증 URL 생성: clientId=***.apps.googleusercontent.com
INFO  - Google OAuth 토큰 교환 시작: code=***...
INFO  - Google OAuth 토큰 교환 성공: email=user@gmail.com
INFO  - 새 사용자 등록 완료: googleId=***, email=user@gmail.com
```

### 5.2 API 호출 로그
```
INFO  - solved.ac 핸들 검증 시작: handle=goddold
INFO  - solved.ac 핸들 검증 성공: handle=goddold, exists=true
INFO  - 사용자 핸들 등록 완료: userId=1, handle=goddold
INFO  - solved.ac 데이터 동기화 시작: userId=1, handle=goddold
INFO  - solved.ac 사용자 정보 조회 성공: handle=goddold, solvedCount=350
```

### 5.3 에러 로그 확인
```
ERROR - Google OAuth 토큰 교환 실패: invalid_grant
ERROR - solved.ac 핸들 검증 실패: handle=invalid_handle, 사용자를 찾을 수 없음
WARN  - 도메인 예외 발생: 이미 동기화가 진행 중입니다
ERROR - solved.ac API 호출 실패: 429 Too Many Requests
```

## 6. 트러블슈팅

### 6.1 일반적인 문제들
- **"redirect_uri_mismatch" 오류**: Google Cloud Console의 승인된 리디렉션 URI와 설정이 일치하는지 확인
- **"invalid_client" 오류**: Google Client ID/Secret이 올바르게 설정되었는지 확인
- **"access_blocked" 오류**: OAuth 동의 화면이 제대로 설정되었는지 확인
- **JWT 토큰 만료**: 토큰 만료 시간 확인 및 리프레시 로직 구현 필요
- **solved.ac 핸들 오류**: 존재하지 않는 핸들이거나 공개되지 않은 프로필

### 6.2 디버깅 팁
- 브라우저 개발자 도구에서 네트워크 탭으로 HTTP 요청/응답 확인
- 쿠키에 JWT 토큰이 올바르게 설정되었는지 확인
- 로그 레벨을 DEBUG로 설정하여 상세한 로그 확인

## 7. 다음 단계

Google OAuth 테스트가 완료되면:
1. **solved.ac 핸들 등록 및 검증** 테스트
2. **사용자 데이터 동기화** E2E 테스트  
3. **대시보드 기능** 통합 테스트
4. **AI 코드 리뷰 기능** 구현 및 테스트
5. 프론트엔드 연동 테스트

---

**⚠️ 보안 주의사항**
- Google Client Secret은 절대 클라이언트 사이드에 노출하지 말 것
- JWT Secret은 충분히 복잡하고 긴 키 사용 (최소 256비트)
- 프로덕션 환경에서는 HTTPS 필수
- Google OAuth 동의 화면을 프로덕션용으로 승인 받기
- 환경 변수는 `.env` 파일을 `.gitignore`에 추가하여 버전 관리에서 제외