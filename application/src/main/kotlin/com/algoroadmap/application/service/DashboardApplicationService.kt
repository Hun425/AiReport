package com.algoroadmap.application.service

import com.algoroadmap.application.dto.DashboardResponse
import com.algoroadmap.application.dto.DashboardUserInfo
import com.algoroadmap.application.dto.RecentReviewData
import com.algoroadmap.application.dto.TagAnalysisData
import com.algoroadmap.domain.exception.DomainException
import com.algoroadmap.domain.service.DashboardService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DashboardApplicationService(
    private val dashboardService: DashboardService
) {
    
    /**
     * 사용자의 대시보드 데이터 조회
     */
    fun getDashboardData(userId: Long): DashboardResponse {
        // 1. 사용자 기본 정보 및 동기화 상태 확인
        val userResult = dashboardService.getUserDashboardInfo(userId)
            ?: throw DomainException.UserNotFoundException(userId)
        
        // 2. 동기화 데이터가 없으면 예외 발생
        if (!userResult.hasSyncedData) {
            throw DomainException.SyncDataNotAvailableException("동기화된 데이터가 없습니다. 먼저 데이터 동기화를 진행해주세요.")
        }
        
        val user = userResult.user
        
        // 3. 사용자 기본 정보 생성
        val userInfo = DashboardUserInfo(
            solvedAcHandle = user.solvedAcHandle,
            profileImageUrl = user.profileImageUrl,
            solvedAcClass = user.solvedAcClass,
            solvedCount = userResult.totalSolvedProblems
        )
        
        // 4. 알고리즘 유형별 분석 데이터 조회 (원형 그래프용으로 최적화됨)
        val tagAnalysisMap = dashboardService.getUserTagAnalysis(userId)
        val tagAnalysis = tagAnalysisMap.values.map { result ->
            TagAnalysisData.create(
                tag = result.tag,
                solved = result.solvedCount,
                total = result.totalProblemsInTag
            )
        }.sortedByDescending { it.solved } // 푼 문제 수 기준으로 정렬
        
        // 5. 최근 코드 리뷰 목록 조회
        val recentReviewResults = dashboardService.getRecentReviews(userId, 5)
        val recentReviews = recentReviewResults.map { result ->
            RecentReviewData(
                reviewId = result.reviewId,
                problemId = result.problemId,
                problemTitle = result.problemTitle,
                createdAt = result.createdAt
            )
        }
        
        return DashboardResponse(
            userInfo = userInfo,
            tagAnalysis = tagAnalysis,
            recentReviews = recentReviews
        )
    }
    
    /**
     * 상세 태그 분석 데이터 조회 (필요시 별도 API로 제공)
     * 원형 그래프 외에 더 자세한 분석을 위한 메서드
     */
    fun getDetailedTagAnalysis(userId: Long, includeMinorTags: Boolean = false): List<TagAnalysisData> {
        val userResult = dashboardService.getUserDashboardInfo(userId)
            ?: throw DomainException.UserNotFoundException(userId)
        
        if (!userResult.hasSyncedData) {
            throw DomainException.SyncDataNotAvailableException("동기화된 데이터가 없습니다.")
        }
        
        val tagAnalysisMap = dashboardService.getUserTagAnalysis(userId)
        var tagAnalysisData = tagAnalysisMap.values.map { result ->
            TagAnalysisData.create(
                tag = result.tag,
                solved = result.solvedCount,
                total = result.totalProblemsInTag
            )
        }.sortedByDescending { it.solved }
        
        // 소량 태그 제외 옵션 (UX 개선용)
        if (!includeMinorTags) {
            tagAnalysisData = tagAnalysisData.filter { it.solved >= 2 } // 2개 미만 푼 태그 제외
        }
        
        return tagAnalysisData
    }
}