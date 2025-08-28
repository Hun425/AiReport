package com.algoroadmap.application.dto

import java.time.LocalDateTime

/**
 * 대시보드 응답 DTO
 */
data class DashboardResponse(
    val userInfo: DashboardUserInfo,
    val tagAnalysis: List<TagAnalysisData>,
    val recentReviews: List<RecentReviewData>
)

/**
 * 대시보드용 사용자 기본 정보
 */
data class DashboardUserInfo(
    val solvedAcHandle: String,
    val profileImageUrl: String?,
    val solvedAcClass: Int,
    val solvedCount: Int
)

/**
 * 알고리즘 유형별 분석 데이터 (레이더 차트용)
 */
data class TagAnalysisData(
    val tag: String,
    val solved: Int,
    val total: Int,
    val percentage: Double
) {
    companion object {
        fun create(tag: String, solved: Int, total: Int): TagAnalysisData {
            val percentage = if (total > 0) (solved.toDouble() / total * 100) else 0.0
            return TagAnalysisData(tag, solved, total, percentage)
        }
    }
}

/**
 * 최근 코드 리뷰 데이터
 */
data class RecentReviewData(
    val reviewId: Long,
    val problemId: Long,
    val problemTitle: String,
    val createdAt: LocalDateTime
)