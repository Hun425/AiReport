package com.algoroadmap.domain.service

/**
 * OAuth 인증 서비스 도메인 인터페이스
 */
interface OAuthService {
    
    /**
     * OAuth 인증 URL 생성
     */
    fun getAuthorizationUrl(state: String? = null): String
    
    /**
     * OAuth 토큰 응답 데이터
     */
    data class TokenResponse(
        val accessToken: String,
        val tokenType: String,
        val expiresIn: Long,
        val refreshToken: String?,
        val scope: String
    )
    
    /**
     * OAuth 사용자 정보
     */
    data class UserInfo(
        val handle: String,
        val bio: String?,
        val profileImageUrl: String?,
        val solvedCount: Int,
        val tier: Int,
        val rating: Int,
        val rank: Int
    )
    
    /**
     * 인증 코드로 액세스 토큰 획득
     */
    suspend fun exchangeCodeForToken(code: String): TokenResponse
    
    /**
     * 액세스 토큰으로 사용자 정보 조회
     */
    suspend fun getUserInfo(accessToken: String): UserInfo
}