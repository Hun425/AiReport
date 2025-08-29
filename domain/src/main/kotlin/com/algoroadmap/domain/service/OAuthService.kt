package com.algoroadmap.domain.service

/**
 * OAuth 인증 서비스 도메인 인터페이스
 */
interface OAuthService {
    
    /**
     * Google OAuth 인증 URL 생성
     */
    fun getGoogleAuthorizationUrl(state: String? = null): String
    
    /**
     * OAuth 토큰 응답 데이터
     */
    data class TokenResponse(
        val accessToken: String,
        val tokenType: String = "Bearer",
        val expiresIn: Int? = null
    )
    
    /**
     * Google 사용자 정보
     */
    data class GoogleUserInfo(
        val sub: String,      // Google ID
        val email: String,    // 이메일
        val name: String,     // 이름
        val picture: String?  // 프로필 이미지
    )
    
    /**
     * Google OAuth 인증 코드로 액세스 토큰 획득
     */
    suspend fun exchangeGoogleCodeForToken(code: String): TokenResponse
    
    /**
     * Google 액세스 토큰으로 사용자 정보 조회
     */
    suspend fun getGoogleUserInfo(accessToken: String): GoogleUserInfo
}