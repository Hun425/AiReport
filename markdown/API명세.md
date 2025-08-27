# API 명세서 (v1.1) - 개선된 버전

이 문서는 AI 알고리즘 학습 로드맵 서비스의 API 엔드포인트를 정의합니다.

**Base URL:** `/api/v1`

## 공통 응답 구조

### 성공 응답
모든 성공 응답은 HTTP 상태 코드와 함께 JSON 형태로 반환됩니다.

### 에러 응답
모든 에러 응답은 다음과 같은 공통 구조를 가집니다:

```json
{
  "error": {
    "code": "ERROR_CODE",
    "message": "사용자에게 보여줄 메시지",
    "details": "상세 에러 정보 (개발자용)"
  }
}
```

**주요 에러 코드:**
- `UNAUTHORIZED`: 인증이 필요함 (401)
- `FORBIDDEN`: 권한 없음 (403)
- `NOT_FOUND`: 리소스를 찾을 수 없음 (404)
- `VALIDATION_ERROR`: 입력 데이터 검증 실패 (400)
- `RATE_LIMIT_EXCEEDED`: API 호출 한도 초과 (429)
- `SYNC_IN_PROGRESS`: 동기화 진행 중 (409)
- `EXTERNAL_API_ERROR`: 외부 API 오류 (502)

### Rate Limiting
모든 API 응답에는 다음 헤더가 포함됩니다:

```
X-RateLimit-Limit: 1000
X-RateLimit-Remaining: 999
X-RateLimit-Reset: 1640995200
```

**제한 사항:**
- 일반 API: 시간당 1000회
- AI 코드 리뷰 API: 일일 10회 (무료 사용자), 일일 100회 (프리미엄 사용자)

## 1. 인증 (Authentication)

### 1.1. solved.ac OAuth 시작

- **Endpoint:** `GET /auth/solvedac`
- **Description:** 사용자를 solved.ac의 OAuth 인증 페이지로 리디렉션시킵니다.
- **Response:**
    - `302 Found` (Redirect)
- **Error:**
    - `500 Internal Server Error`: OAuth 설정 오류

### 1.2. solved.ac OAuth 콜백 처리

- **Endpoint:** `GET /auth/solvedac/callback`
- **Description:** solved.ac로부터 인증 코드를 받아 우리 서비스의 JWT(JSON Web Token)를 발급하고 쿠키에 저장합니다. 성공 시 사용자를 온보딩 페이지(`/onboarding`)로 리디렉션합니다.
- **Query Parameters:**
    - `code`: `string` (required) - 인증 코드
- **Response:**
    - `302 Found` (Redirect to `/onboarding`)
- **Error:**
    - `400 Bad Request`: 인증 코드 누락 또는 유효하지 않음
    - `502 Bad Gateway`: solved.ac API 통신 오류

## 2. 사용자 및 데이터 동기화 (User & Sync)

### 2.1. 내 정보 조회

- **Endpoint:** `GET /users/me`
- **Description:** 현재 로그인된 사용자의 기본 정보를 조회합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Response:** `200 OK`

```json
{
  "solvedAcHandle": "goddold",
  "profileImageUrl": "https://...",
  "class": 5,
  "solvedCount": 350,
  "rank": 12345,
  "lastSyncedAt": "2025-08-27T22:10:00",
  "subscription": {
    "plan": "free", // "free" | "premium"
    "dailyReviewLimit": 10,
    "dailyReviewUsed": 3
  }
}
```

- **Error:**
    - `401 Unauthorized`: JWT 토큰 누락 또는 만료
    - `404 Not Found`: 사용자 정보 없음

### 2.2. solved.ac 데이터 동기화 요청

- **Endpoint:** `POST /users/me/sync`
- **Description:** 사용자의 solved.ac 문제 풀이 데이터를 서버에 비동기적으로 동기화하도록 요청합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Response:** `202 Accepted`

```json
{
  "message": "Synchronization has been started.",
  "estimatedDuration": "약 1분"
}
```

- **Error:**
    - `409 Conflict`: 이미 동기화 진행 중
    - `502 Bad Gateway`: solved.ac API 통신 오류

### 2.3. 동기화 상태 조회

- **Endpoint:** `GET /users/me/sync/status`
- **Description:** 현재 동기화 진행 상황을 조회합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Response:** `200 OK`

```json
{
  "status": "in_progress", // "idle" | "in_progress" | "completed" | "failed"
  "progress": 75,
  "message": "푼 문제 목록 가져오는 중...",
  "startedAt": "2025-08-27T22:10:00",
  "completedAt": null
}
```

## 3. 대시보드 (Dashboard)

### 3.1. 대시보드 데이터 조회

- **Endpoint:** `GET /dashboard/me`
- **Description:** 메인 대시보드에 필요한 모든 데이터를 조회합니다. (내 정보 + 유형별 분석 데이터)
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Response:** `200 OK`

```json
{
  "userInfo": {
    "solvedAcHandle": "goddold",
    "profileImageUrl": "https://...",
    "class": 5,
    "solvedCount": 350
  },
  "tagAnalysis": [ // 레이더 차트용 데이터
    { "tag": "DP", "solved": 50, "total": 100, "percentage": 50 },
    { "tag": "그래프 탐색", "solved": 80, "total": 100, "percentage": 80 },
    { "tag": "구현", "solved": 75, "total": 120, "percentage": 62.5 },
    { "tag": "그리디", "solved": 30, "total": 60, "percentage": 50 },
    { "tag": "문자열", "solved": 60, "total": 80, "percentage": 75 }
  ],
  "recentReviews": [
    { "reviewId": 1, "problemId": 1000, "problemTitle": "A+B", "createdAt": "2025-08-27T20:00:00" },
    { "reviewId": 2, "problemId": 1753, "problemTitle": "최단경로", "createdAt": "2025-08-27T19:00:00" }
  ]
}
```

- **Error:**
    - `400 Bad Request`: 동기화되지 않은 사용자
    - `401 Unauthorized`: 인증 실패

## 4. 로드맵 (Roadmap)

### 4.1. 기업 목록 조회

- **Endpoint:** `GET /companies`
- **Description:** 로드맵 생성 시 선택할 수 있는 기업 목록을 조회합니다.
- **Query Parameters:**
    - `page`: `number` (default: 1) - 페이지 번호
    - `size`: `number` (default: 20, max: 100) - 페이지 크기
- **Response:** `200 OK`

```json
{
  "data": [
    { "id": 1, "name": "카카오", "logoUrl": "...", "description": "카카오 코딩테스트 대비" },
    { "id": 2, "name": "네이버", "logoUrl": "...", "description": "네이버 코딩테스트 대비" }
  ],
  "pagination": {
    "page": 1,
    "size": 20,
    "total": 25,
    "totalPages": 2,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

### 4.2. 로드맵 생성

- **Endpoint:** `POST /roadmaps`
- **Description:** 목표 기업과 기간을 설정하여 새로운 로드맵을 생성합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Request Body:**

```json
{
  "companyId": 1,
  "durationInMonths": 3
}
```

- **Response:** `201 Created`

```json
{
  "roadmapId": 123,
  "message": "로드맵이 성공적으로 생성되었습니다."
}
```

- **Error:**
    - `400 Bad Request`: 필수 파라미터 누락 또는 유효하지 않은 값
    - `409 Conflict`: 이미 활성화된 로드맵 존재

### 4.3. 내 로드맵 조회

- **Endpoint:** `GET /roadmaps/me`
- **Description:** 현재 사용자가 진행 중인 로드맵의 상세 내용을 조회합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Query Parameters:**
    - `week`: `number` (optional) - 특정 주차만 조회
- **Response:** `200 OK`

```json
{
  "roadmapId": 123,
  "companyName": "카카오",
  "durationInMonths": 3,
  "totalProgress": 15,
  "createdAt": "2025-08-27T10:00:00",
  "weeks": [
    {
      "weekNumber": 1,
      "title": "DP 기초 다지기",
      "progress": 60,
      "totalProblems": 5,
      "solvedProblems": 3,
      "problems": [
        { 
          "problemId": 2748, 
          "title": "피보나치 수 2", 
          "difficulty": "Silver 5", 
          "isSolved": true, 
          "hasReview": false,
          "bojUrl": "https://www.acmicpc.net/problem/2748"
        },
        { 
          "problemId": 1463, 
          "title": "1로 만들기", 
          "difficulty": "Silver 3", 
          "isSolved": true, 
          "hasReview": true,
          "bojUrl": "https://www.acmicpc.net/problem/1463"
        },
        { 
          "problemId": 2579, 
          "title": "계단 오르기", 
          "difficulty": "Silver 3", 
          "isSolved": false, 
          "hasReview": false,
          "bojUrl": "https://www.acmicpc.net/problem/2579"
        }
      ]
    }
    // ... other weeks
  ]
}
```

- **Error:**
    - `404 Not Found`: 활성화된 로드맵이 없음

## 5. AI 코드 리뷰 (AI Code Review)

### 5.1. 코드 리뷰 요청

- **Endpoint:** `POST /reviews`
- **Description:** 특정 문제에 대한 코드를 제출하여 AI 리뷰를 요청합니다.
- **Headers:**
    - `Authorization: Bearer {jwt_token}`
    - `Content-Type: application/json`
- **Request Body:**

```json
{
  "problemId": 2579,
  "language": "Kotlin",
  "code": "fun main() {\n    val n = readln().toInt()\n    // ...\n}"
}
```

- **Response:** `201 Created`

```json
{
  "reviewId": 3,
  "problemId": 2579,
  "language": "Kotlin",
  "reviewContent": "### 총평\n전반적으로 정확하게 DP의 핵심 원리를 적용했습니다...",
  "createdAt": "2025-08-27T23:00:00",
  "usage": {
    "dailyReviewUsed": 4,
    "dailyReviewLimit": 10
  }
}
```

- **Error:**
    - `400 Bad Request`: 코드 누락 또는 지원하지 않는 언어
    - `404 Not Found`: 존재하지 않는 문제 ID
    - `429 Too Many Requests`: 일일 리뷰 한도 초과
    - `502 Bad Gateway`: AI API 통신 오류

### 5.2. 코드 리뷰 목록 조회

- **Endpoint:** `GET /reviews`
- **Description:** 사용자의 AI 코드 리뷰 내역을 조회합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Query Parameters:**
    - `page`: `number` (default: 1) - 페이지 번호
    - `size`: `number` (default: 10, max: 50) - 페이지 크기
    - `problemId`: `number` (optional) - 특정 문제 필터링
- **Response:** `200 OK`

```json
{
  "data": [
    {
      "reviewId": 3,
      "problemId": 2579,
      "problemTitle": "계단 오르기",
      "language": "Kotlin",
      "createdAt": "2025-08-27T23:00:00"
    },
    {
      "reviewId": 2,
      "problemId": 1463,
      "problemTitle": "1로 만들기",
      "language": "Kotlin",
      "createdAt": "2025-08-27T22:30:00"
    }
  ],
  "pagination": {
    "page": 1,
    "size": 10,
    "total": 15,
    "totalPages": 2,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

### 5.3. 특정 코드 리뷰 조회

- **Endpoint:** `GET /reviews/{reviewId}`
- **Description:** 특정 코드 리뷰의 상세 내용을 조회합니다.
- **Headers:** `Authorization: Bearer {jwt_token}`
- **Path Parameters:**
    - `reviewId`: `number` (required) - 리뷰 ID
- **Response:** `200 OK`

```json
{
  "reviewId": 3,
  "problemId": 2579,
  "problemTitle": "계단 오르기",
  "language": "Kotlin",
  "submittedCode": "fun main() {\n    val n = readln().toInt()\n    // ...\n}",
  "reviewContent": "### 총평\n전반적으로 정확하게 DP의 핵심 원리를 적용했습니다...",
  "createdAt": "2025-08-27T23:00:00"
}
```

- **Error:**
    - `404 Not Found`: 존재하지 않는 리뷰 ID 또는 권한 없음

## 6. 상태 및 헬스체크

### 6.1. API 상태 확인

- **Endpoint:** `GET /health`
- **Description:** API 서버의 상태를 확인합니다.
- **Response:** `200 OK`

```json
{
  "status": "healthy",
  "timestamp": "2025-08-27T23:00:00",
  "version": "1.1.0",
  "services": {
    "database": "healthy",
    "solvedAcApi": "healthy",
    "aiApi": "healthy"
  }
}
```