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
import java.net.URI

@Service
class SolvedAcOAuthServiceImpl(
    @Qualifier("oauthWebClient") private val webClient: WebClient,
    @Value("\${spring.security.oauth2.client.registration.solved-ac.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.solved-ac.client-secret}")
    private val clientSecret: String,
    @Value("\${spring.security.oauth2.client.registration.solved-ac.redirect-uri}")
    private val redirectUri: String
) : OAuthService {
    
    private val logger = LoggerFactory.getLogger(SolvedAcOAuthServiceImpl::class.java)
    
    /**
     * OAuth 인증 URL 생성
     */
    override fun getAuthorizationUrl(state: String?): String {
        val uriBuilder = UriComponentsBuilder
            .fromUriString("https://solved.ac/oauth/authorize")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("response_type", "code")
            .queryParam("scope", "read")
        
        state?.let { uriBuilder.queryParam("state", it) }
        
        val authUrl = uriBuilder.build().toUriString()
        logger.info("OAuth 인증 URL 생성: clientId=$clientId, redirectUri=$redirectUri")
        return authUrl
    }
    
    /**
     * 인증 코드로 액세스 토큰 획득
     */
    override suspend fun exchangeCodeForToken(code: String): OAuthService.TokenResponse {
        return try {
            logger.info("OAuth 토큰 교환 시작: code=${code.take(10)}...")
            val response = webClient
                .post()
                .uri("https://solved.ac/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(
                    "grant_type=authorization_code" +
                    "&client_id=$clientId" +
                    "&client_secret=$clientSecret" +
                    "&redirect_uri=$redirectUri" +
                    "&code=$code"
                )
                .retrieve()
                .awaitBody<SolvedAcTokenResponse>()
                
            logger.info("OAuth 토큰 교환 성공: tokenType=${response.tokenType}")
            OAuthService.TokenResponse(
                accessToken = response.accessToken,
                tokenType = response.tokenType,
                expiresIn = response.expiresIn,
                refreshToken = response.refreshToken,
                scope = response.scope
            )
        } catch (e: Exception) {
            logger.error("OAuth 토큰 교환 실패: code=${code.take(10)}...", e)
            throw e
        }
    }
    
    /**
     * 액세스 토큰으로 사용자 정보 조회
     */
    override suspend fun getUserInfo(accessToken: String): OAuthService.UserInfo {
        return webClient
            .get()
            .uri("https://solved.ac/api/v3/user/me")
            .headers { headers ->
                headers.setBearerAuth(accessToken)
            }
            .retrieve()
            .awaitBody<SolvedAcUserInfo>()
            .let { userInfo ->
                OAuthService.UserInfo(
                    handle = userInfo.handle,
                    bio = userInfo.bio,
                    profileImageUrl = userInfo.profileImageUrl,
                    solvedCount = userInfo.solvedCount,
                    tier = userInfo.tier,
                    rating = userInfo.rating,
                    rank = userInfo.rank
                )
            }
    }
}

/**
 * solved.ac OAuth 토큰 응답
 */
data class SolvedAcTokenResponse(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val refreshToken: String?,
    val scope: String
)

/**
 * solved.ac 사용자 정보
 */
data class SolvedAcUserInfo(
    val handle: String,
    val bio: String?,
    val profileImageUrl: String?,
    val solvedCount: Int,
    val tier: Int,
    val rating: Int,
    val ratingByProblemsSum: Int,
    val ratingByClass: Int,
    val ratingBySolvedCount: Int,
    val rivalCount: Int,
    val reverseRivalCount: Int,
    val maxStreak: Int,
    val rank: Int,
    val isRival: Boolean,
    val isReverseRival: Boolean,
    val badgeId: String?,
    val backgroundId: String?
)