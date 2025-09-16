package com.algoroadmap.application.service

import com.algoroadmap.application.dto.AuthResult
import com.algoroadmap.application.dto.OAuthCallbackRequest
import com.algoroadmap.application.dto.SubscriptionInfo
import com.algoroadmap.application.dto.UserResponse
import com.algoroadmap.domain.entity.User
import com.algoroadmap.domain.exception.DomainException
import com.algoroadmap.domain.vo.Email
import com.algoroadmap.domain.vo.SolvedAcHandle
import com.algoroadmap.domain.repository.UserRepository
import com.algoroadmap.domain.service.SolvedAcService
import com.algoroadmap.domain.service.SolvedAcUserData
import com.algoroadmap.domain.service.TokenService
import com.algoroadmap.domain.service.OAuthService

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val solvedAcService: SolvedAcService,
    private val tokenService: TokenService,
    private val oAuthService: OAuthService
) {

    
    /**
     * Google OAuth 인증 URL 생성
     */
    fun getGoogleAuthorizationUrl(state: String? = null): String {
        return oAuthService.getGoogleAuthorizationUrl(state)
    }
    
    /**
     * Google OAuth 콜백 처리
     */
    suspend fun handleGoogleCallback(request: OAuthCallbackRequest): AuthResult {
        try {
            // 1. 인증 코드로 액세스 토큰 획득
            val tokenResponse = oAuthService.exchangeGoogleCodeForToken(request.code)
            
            // 2. 액세스 토큰으로 Google 사용자 정보 조회
            val googleUserInfo = oAuthService.getGoogleUserInfo(tokenResponse.accessToken)
            
            // 3. 기존 사용자 확인 또는 새 사용자 생성 (Google ID 기반)
            val existingUser = userRepository.findByGoogleId(googleUserInfo.sub)
            val isNewUser = existingUser == null
            
            val user = if (existingUser != null) {
                // 기존 사용자 정보 업데이트
                updateUserFromGoogle(existingUser, googleUserInfo)
            } else {
                // 새 사용자 생성
                createUserFromGoogle(googleUserInfo)
            }
            
            // 4. JWT 토큰 생성
            val accessToken = tokenService.generateToken(user.id, user.email?.value ?: "")

            
            return AuthResult(
                accessToken = accessToken,
                user = user.toUserResponse(),
                isNewUser = isNewUser
            )
            
        } catch (e: Exception) {
            throw DomainException.OAuthAuthenticationException("OAuth 인증 중 오류 발생: ${e.message}", e)
        }
    }
    
    private fun updateUserFromGoogle(
        existingUser: User, 
        googleUserInfo: OAuthService.GoogleUserInfo
    ): User {
        existingUser.email = Email.create(googleUserInfo.email)
        existingUser.profileImageUrl = googleUserInfo.picture
        existingUser.updatedAt = LocalDateTime.now()
        
        return userRepository.save(existingUser)
    }
    
    private fun createUserFromGoogle(
        googleUserInfo: OAuthService.GoogleUserInfo
    ): User {
        val newUser = User(
            googleId = googleUserInfo.sub,
            email = Email.create(googleUserInfo.email),
            profileImageUrl = googleUserInfo.picture,
            solvedAcHandle = null, // 나중에 별도 등록
            solvedAcClass = 0,
            solvedCount = 0,
            rank = 0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        
        return userRepository.save(newUser)
    }
    
    /**
     * JWT 토큰으로 사용자 정보 조회
     */
    fun getUserFromToken(token: String): UserResponse? {
        val userId = tokenService.getUserIdFromToken(token) ?: return null
        val user = userRepository.findById(userId) ?: return null
        return user.toUserResponse()
    }
    
    /**
     * 사용자 ID로 사용자 정보 조회
     */
    fun getUserById(userId: Long): UserResponse? {
        val user = userRepository.findById(userId) ?: return null
        return user.toUserResponse()
    }
    
    /**
     * 토큰 유효성 검증
     */
    fun validateToken(token: String): Boolean {
        return tokenService.validateToken(token)
    }
    
    /**
     * solved.ac 핸들 등록/수정
     */
    suspend fun updateSolvedAcHandle(userId: Long, handle: String) {
        // 1. solved.ac API로 핸들 유효성 검증
        val solvedAcUser = solvedAcService.fetchUserData(handle)
            ?: throw DomainException.UserNotFoundByHandleException(handle)
        
        // 2. 사용자 조회
        val user = userRepository.findById(userId)
            ?: throw DomainException.UserNotFoundException(userId)
        
        // 3. 사용자 정보 업데이트
        user.solvedAcHandle = SolvedAcHandle.create(handle)
        user.profileImageUrl = solvedAcUser.profileImageUrl
        user.solvedAcClass = solvedAcUser.solvedAcClass
        user.solvedCount = solvedAcUser.solvedCount
        user.rank = solvedAcUser.rank
        user.updatedAt = LocalDateTime.now()
        
        userRepository.save(user)
    }
}

/**
 * User 엔티티를 UserResponse DTO로 변환하는 확장 함수
 */
private fun User.toUserResponse(): UserResponse {
    return UserResponse(
        id = this.id,
        email = this.email?.value,
        solvedAcHandle = this.solvedAcHandle?.value,
        profileImageUrl = this.profileImageUrl,
        solvedAcClass = this.solvedAcClass,
        solvedCount = this.solvedCount,
        rank = this.rank,
        lastSyncedAt = this.lastSyncedAt,
        subscription = SubscriptionInfo(
            plan = this.subscriptionPlan,
            dailyReviewLimit = this.getDailyReviewLimit(),
            dailyReviewUsed = this.dailyReviewUsed
        )
    )
}