package com.algoroadmap.application.dto

import com.algoroadmap.domain.entity.SubscriptionPlan
import java.time.LocalDateTime

/**
 * OAuth 인증 결과 DTO
 */
data class AuthResult(
    val accessToken: String,
    val user: UserResponse,
    val isNewUser: Boolean
)

/**
 * 사용자 정보 응답 DTO
 */
data class UserResponse(
    val id: Long,
    val email: String? = null,
    val solvedAcHandle: String? = null,
    val profileImageUrl: String?,
    val solvedAcClass: Int = 0,
    val solvedCount: Int = 0,
    val rank: Int = 0,
    val lastSyncedAt: LocalDateTime?,
    val subscription: SubscriptionInfo
)

/**
 * 구독 정보 DTO
 */
data class SubscriptionInfo(
    val plan: SubscriptionPlan,
    val dailyReviewLimit: Int,
    val dailyReviewUsed: Int
)

/**
 * OAuth 콜백 요청 DTO
 */
data class OAuthCallbackRequest(
    val code: String,
    val state: String? = null
)

/**
 * solved.ac 핸들 업데이트 요청 DTO
 */
data class UpdateSolvedAcHandleRequest(
    val handle: String
)

