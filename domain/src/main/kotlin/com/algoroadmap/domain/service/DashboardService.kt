package com.algoroadmap.domain.service

import com.algoroadmap.domain.entity.User
import com.algoroadmap.domain.repository.CodeReviewRepository
import com.algoroadmap.domain.repository.UserRepository
import com.algoroadmap.domain.repository.UserSolvedProblemRepository
import java.time.LocalDateTime

/**
 * 대시보드 관련 도메인 서비스
 */
interface DashboardService {
    
    /**
     * 사용자의 알고리즘 유형별 분석 데이터 조회
     */
    fun getUserTagAnalysis(userId: Long): Map<String, TagAnalysisResult>
    
    /**
     * 사용자의 최근 코드 리뷰 목록 조회
     */
    fun getRecentReviews(userId: Long, limit: Int = 5): List<RecentReviewResult>
    
    /**
     * 사용자의 대시보드 기본 정보 조회 (동기화 여부 확인 포함)
     */
    fun getUserDashboardInfo(userId: Long): DashboardUserResult?
}

/**
 * 알고리즘 유형별 분석 결과
 */
data class TagAnalysisResult(
    val tag: String,
    val solvedCount: Int,
    val totalProblemsInTag: Int
)

/**
 * 최근 리뷰 결과
 */
data class RecentReviewResult(
    val reviewId: Long,
    val problemId: Long,
    val problemTitle: String,
    val createdAt: LocalDateTime
)

/**
 * 대시보드 사용자 정보 결과
 */
data class DashboardUserResult(
    val user: User,
    val hasSyncedData: Boolean,
    val totalSolvedProblems: Int
)