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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val solvedAcService: SolvedAcService,
    private val tokenService: TokenService
) {
    
    /**
     * solved.ac OAuth 콜백 처리
     */
    suspend fun handleSolvedAcCallback(request: OAuthCallbackRequest): AuthResult {
        // TODO: 실제로는 OAuth code를 사용해 access_token을 획득해야 함
        // 지금은 Mock으로 구현
        
        // Mock: code에서 사용자 핸들을 추출 (실제로는 OAuth 서버에서 사용자 정보 조회)
        val mockHandle = "goddold" // 실제로는 OAuth 서버 응답에서 추출
        
        // solved.ac API에서 사용자 정보 조회
        val solvedAcUser = solvedAcService.fetchUserData(mockHandle)
            ?: throw DomainException.UserNotFoundByHandleException(mockHandle)
        
        // 기존 사용자 확인 또는 새 사용자 생성
        val existingUser = userRepository.findByHandle(solvedAcUser.handle)
        val isNewUser = existingUser == null
        
        val user = if (existingUser != null) {
            // 기존 사용자 정보 업데이트
            updateUserFromSolvedAc(existingUser, solvedAcUser)
        } else {
            // 새 사용자 생성
            createUserFromSolvedAc(solvedAcUser)
        }
        
        // JWT 토큰 생성
        val accessToken = tokenService.generateToken(user.id, user.solvedAcHandle)
        
        return AuthResult(
            accessToken = accessToken,
            user = user.toUserResponse(),
            isNewUser = isNewUser
        )
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