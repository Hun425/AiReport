package com.algoroadmap.infrastructure.external

import com.algoroadmap.domain.service.OAuthService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Service
class GoogleOAuthServiceImpl(
    @Qualifier("oauthWebClient") private val webClient: WebClient,
    @param:Value("\${spring.security.oauth2.client.registration.google.client-id}")
    private val clientId: String,
    @param:Value("\${spring.security.oauth2.client.registration.google.client-secret}")
    private val clientSecret: String,
    @param:Value("\${spring.security.oauth2.client.registration.google.redirect-uri}")
    private val redirectUri: String
) : OAuthService {
    
    private val logger = LoggerFactory.getLogger(GoogleOAuthServiceImpl::class.java)
    
    /**
     * Google OAuth 인증 URL 생성
     */
    override fun getGoogleAuthorizationUrl(state: String?): String {
        val uriBuilder = UriComponentsBuilder
            .fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("response_type", "code")
            .queryParam("scope", "openid profile email")
            .queryParam("access_type", "offline")
        
        state?.let { uriBuilder.queryParam("state", it) }
        
        val authUrl = uriBuilder.build().toUriString()
        logger.info("Google OAuth 인증 URL 생성: clientId=${clientId.take(20)}...")
        return authUrl
    }
    
    /**
     * Google OAuth 인증 코드로 액세스 토큰 획득
     */
    override suspend fun exchangeGoogleCodeForToken(code: String): OAuthService.TokenResponse {
        return try {
            logger.info("Google OAuth 토큰 교환 시작: code=${code.take(10)}...")
            val response = webClient
                .post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(
                    "grant_type=authorization_code" +
                    "&client_id=$clientId" +
                    "&client_secret=$clientSecret" +
                    "&redirect_uri=$redirectUri" +
                    "&code=$code"
                )
                .retrieve()
                .awaitBody<GoogleTokenResponse>()
                
            logger.info("Google OAuth 토큰 교환 성공: tokenType=${response.tokenType}")
            OAuthService.TokenResponse(
                accessToken = response.accessToken,
                tokenType = response.tokenType,
                expiresIn = response.expiresIn
            )
        } catch (e: Exception) {
            logger.error("Google OAuth 토큰 교환 실패: code=${code.take(10)}...", e)
            throw e
        }
    }
    
    /**
     * Google 액세스 토큰으로 사용자 정보 조회
     */
    override suspend fun getGoogleUserInfo(accessToken: String): OAuthService.GoogleUserInfo {
        return try {
            logger.info("Google 사용자 정보 조회 시작")
            val response = webClient
                .get()
                .uri("https://www.googleapis.com/oauth2/v2/userinfo")
                .headers { headers ->
                    headers.setBearerAuth(accessToken)
                }
                .retrieve()
                .awaitBody<GoogleUserResponse>()
                
            logger.info("Google 사용자 정보 조회 성공: email=${response.email}")
            OAuthService.GoogleUserInfo(
                sub = response.id,
                email = response.email,
                name = response.name,
                picture = response.picture
            )
        } catch (e: Exception) {
            logger.error("Google 사용자 정보 조회 실패", e)
            throw e
        }
    }
}

/**
 * Google OAuth 토큰 응답
 */
data class GoogleTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String? = null
) {
    val accessToken: String get() = access_token
    val tokenType: String get() = token_type
    val expiresIn: Int get() = expires_in
}

/**
 * Google 사용자 정보 응답
 */
data class GoogleUserResponse(
    val id: String,
    val email: String,
    val name: String,
    val picture: String?,
    val verified_email: Boolean? = null
)