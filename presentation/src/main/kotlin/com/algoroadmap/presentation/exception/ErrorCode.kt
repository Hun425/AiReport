package com.algoroadmap.presentation.exception

import org.springframework.http.HttpStatus

/**
 * API 에러 코드 정의
 */
enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus
) {
    // 인증/인가 관련
    UNAUTHORIZED("UNAUTHORIZED", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("FORBIDDEN", "권한이 없습니다.", HttpStatus.FORBIDDEN),
    OAUTH_AUTHENTICATION_FAILED("OAUTH_AUTHENTICATION_FAILED", "OAuth 인증에 실패했습니다.", HttpStatus.BAD_REQUEST),
    
    // 사용자 관련
    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_BY_HANDLE("USER_NOT_FOUND_BY_HANDLE", "핸들로 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    
    // 동기화 관련
    SYNC_IN_PROGRESS("SYNC_IN_PROGRESS", "이미 동기화가 진행 중입니다.", HttpStatus.CONFLICT),
    SYNC_START_FAILED("SYNC_START_FAILED", "동기화 시작에 실패했습니다.", HttpStatus.BAD_REQUEST),
    
    // 기업/로드맵 관련
    COMPANY_NOT_FOUND("COMPANY_NOT_FOUND", "기업을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ROADMAP_NOT_FOUND("ROADMAP_NOT_FOUND", "활성화된 로드맵이 없습니다.", HttpStatus.NOT_FOUND),
    
    // 코드 리뷰 관련
    CODE_REVIEW_NOT_FOUND("CODE_REVIEW_NOT_FOUND", "코드 리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DAILY_REVIEW_LIMIT_EXCEEDED("DAILY_REVIEW_LIMIT_EXCEEDED", "일일 코드 리뷰 한도를 초과했습니다.", HttpStatus.TOO_MANY_REQUESTS),
    
    // 제한/할당량 관련
    RATE_LIMIT_EXCEEDED("RATE_LIMIT_EXCEEDED", "API 호출 한도를 초과했습니다.", HttpStatus.TOO_MANY_REQUESTS),
    
    // 외부 API 관련
    EXTERNAL_API_ERROR("EXTERNAL_API_ERROR", "외부 서비스 연동 중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    
    // 유효성 검증 관련
    VALIDATION_ERROR("VALIDATION_ERROR", "입력 데이터 검증에 실패했습니다.", HttpStatus.BAD_REQUEST),
    
    // 일반 오류
    DOMAIN_ERROR("DOMAIN_ERROR", "도메인 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    
    companion object {
        /**
         * 도메인 예외를 ErrorCode로 매핑
         */
        fun fromDomainException(exception: com.algoroadmap.domain.exception.DomainException): ErrorCode {
            return when (exception) {
                is com.algoroadmap.domain.exception.DomainException.UserNotFoundException -> USER_NOT_FOUND
                is com.algoroadmap.domain.exception.DomainException.UserNotFoundByHandleException -> USER_NOT_FOUND_BY_HANDLE
                is com.algoroadmap.domain.exception.DomainException.SyncInProgressException -> SYNC_IN_PROGRESS
                is com.algoroadmap.domain.exception.DomainException.RateLimitExceededException -> RATE_LIMIT_EXCEEDED
                is com.algoroadmap.domain.exception.DomainException.CompanyNotFoundException -> COMPANY_NOT_FOUND
                is com.algoroadmap.domain.exception.DomainException.RoadmapNotFoundException -> ROADMAP_NOT_FOUND
                is com.algoroadmap.domain.exception.DomainException.CodeReviewNotFoundException -> CODE_REVIEW_NOT_FOUND
                is com.algoroadmap.domain.exception.DomainException.DailyReviewLimitExceededException -> DAILY_REVIEW_LIMIT_EXCEEDED
                is com.algoroadmap.domain.exception.DomainException.OAuthAuthenticationException -> OAUTH_AUTHENTICATION_FAILED
                else -> DOMAIN_ERROR
            }
        }
    }
}