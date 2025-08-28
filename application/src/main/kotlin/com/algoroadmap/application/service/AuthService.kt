package com.algoroadmap.application.service

import com.algoroadmap.application.dto.AuthResult
import com.algoroadmap.application.dto.OAuthCallbackRequest
import com.algoroadmap.application.dto.SubscriptionInfo
import com.algoroadmap.application.dto.UserResponse
import com.algoroadmap.domain.entity.User
import com.algoroadmap.domain.exception.DomainException
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
     * solved.ac OAuth 인증 URL 생성
     */
    fun getAuthorizationUrl(state: String? = null): String {
        return oAuthService.getAuthorizationUrl(state)
    }
    
    /**
     * solved.ac OAuth 콜백 처리
     */
    suspend fun handleSolvedAcCallback(request: OAuthCallbackRequest): AuthResult {
        try {
            // 1. 인증 코드로 액세스 토큰 획득
            val tokenResponse = oAuthService.exchangeCodeForToken(request.code)
            
            // 2. 액세스 토큰으로 사용자 정보 조회
            val userInfo = oAuthService.getUserInfo(tokenResponse.accessToken)
            
            // 3. solved.ac API에서 상세 사용자 데이터 조회
            val solvedAcUser = solvedAcService.fetchUserData(userInfo.handle)
                ?: throw DomainException.UserNotFoundByHandleException(userInfo.handle)
            
            // 4. 기존 사용자 확인 또는 새 사용자 생성
            val existingUser = userRepository.findByHandle(userInfo.handle)
            val isNewUser = existingUser == null
            
            val user = if (existingUser != null) {
                // 기존 사용자 정보 업데이트
                updateUserFromSolvedAc(existingUser, solvedAcUser)
            } else {
                // 새 사용자 생성
                createUserFromSolvedAc(solvedAcUser)
            }
            
            // 5. JWT 토큰 생성
            val accessToken = tokenService.generateToken(user.id, user.solvedAcHandle)
            
            return AuthResult(
                accessToken = accessToken,
                user = user.toUserResponse(),
                isNewUser = isNewUser
            )
            
        } catch (e: Exception) {
            throw DomainException.OAuthAuthenticationException("OAuth 인증 중 오류 발생: ${e.message}", e)
        }
    }
    
    private fun updateUserFromSolvedAc(
        existingUser: User, 
        solvedAcUser: SolvedAcUserData
    ): User {
        val updatedUser = existingUser.copy(
            profileImageUrl = solvedAcUser.profileImageUrl,
            solvedAcClass = solvedAcUser.solvedAcClass,
            solvedCount = solvedAcUser.solvedCount,
            rank = solvedAcUser.rank,
            updatedAt = LocalDateTime.now()
        )
        
        return userRepository.save(updatedUser)
    }
    
    private fun createUserFromSolvedAc(
        solvedAcUser: SolvedAcUserData
    ): User {
        val newUser = User(
            solvedAcHandle = solvedAcUser.handle,
            profileImageUrl = solvedAcUser.profileImageUrl,
            solvedAcClass = solvedAcUser.solvedAcClass,
            solvedCount = solvedAcUser.solvedCount,
            rank = solvedAcUser.rank,
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
     * 토큰 유효성 검증
     */
    fun validateToken(token: String): Boolean {
        return tokenService.validateToken(token)
    }
}

/**
 * User 엔티티를 UserResponse DTO로 변환하는 확장 함수
 */
private fun User.toUserResponse(): UserResponse {
    return UserResponse(
        id = this.id,
        solvedAcHandle = this.solvedAcHandle,
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