package com.algoroadmap.domain.exception

sealed class DomainException(message: String, cause: Throwable? = null) : RuntimeException(message, cause) {
    class UserNotFoundException(userId: Long) : DomainException("사용자를 찾을 수 없습니다: $userId")
    class UserNotFoundByHandleException(handle: String) : DomainException("사용자를 찾을 수 없습니다: $handle")
    class SyncInProgressException : DomainException("동기화가 진행 중입니다")
    class RateLimitExceededException : DomainException("API 호출 한도를 초과했습니다")
    class CompanyNotFoundException(companyId: Long) : DomainException("기업을 찾을 수 없습니다: $companyId")
    class RoadmapNotFoundException(userId: Long) : DomainException("활성화된 로드맵이 없습니다: $userId")
    class CodeReviewNotFoundException(reviewId: Long) : DomainException("코드 리뷰를 찾을 수 없습니다: $reviewId")
    class DailyReviewLimitExceededException : DomainException("일일 코드 리뷰 한도를 초과했습니다")
    class SyncDataNotAvailableException(message: String) : DomainException(message)
    class OAuthAuthenticationException(message: String, cause: Throwable? = null) : DomainException(message, cause)
    
    // 로드맵 관련 예외
    class ActiveRoadmapAlreadyExistsException : DomainException("이미 활성화된 로드맵이 존재합니다")
    class UnauthorizedRoadmapAccessException : DomainException("로드맵에 접근할 권한이 없습니다")
    class InvalidCompanyIdException : DomainException("잘못된 기업 ID입니다")
    class InvalidDurationException : DomainException("학습 기간은 1-12개월 사이여야 합니다")
}