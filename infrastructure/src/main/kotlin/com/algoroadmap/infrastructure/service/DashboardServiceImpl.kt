package com.algoroadmap.infrastructure.service

import com.algoroadmap.domain.repository.CodeReviewRepository
import com.algoroadmap.domain.repository.UserRepository
import com.algoroadmap.domain.repository.UserSolvedProblemRepository
import com.algoroadmap.domain.service.DashboardService
import com.algoroadmap.domain.service.DashboardUserResult
import com.algoroadmap.domain.service.RecentReviewResult
import com.algoroadmap.domain.service.TagAnalysisResult
import org.springframework.stereotype.Service

@Service
class DashboardServiceImpl(
    private val userRepository: UserRepository,
    private val userSolvedProblemRepository: UserSolvedProblemRepository,
    private val codeReviewRepository: CodeReviewRepository
) : DashboardService {
    
    override fun getUserTagAnalysis(userId: Long): Map<String, TagAnalysisResult> {
        // 사용자가 푼 문제의 태그별 개수 조회
        val userTagCounts = userSolvedProblemRepository.findTagAnalysByUserId(userId)
        
        // 각 태그별로 TagAnalysisResult 생성
        // TODO: 전체 문제 수는 나중에 문제 데이터가 더 많아지면 실제 계산하도록 개선
        val tagTotalProblems = mapOf(
            "DP" to 100,
            "그래프 탐색" to 100,
            "구현" to 120,
            "그리디" to 60,
            "문자열" to 80,
            "수학" to 90,
            "정렬" to 40,
            "이분탐색" to 50,
            "브루트포스" to 70,
            "자료구조" to 85
        )
        
        return userTagCounts.mapValues { (tag, solvedCount) ->
            val totalInTag = tagTotalProblems[tag] ?: 100 // 기본값
            TagAnalysisResult(
                tag = tag,
                solvedCount = solvedCount,
                totalProblemsInTag = totalInTag
            )
        }
    }
    
    override fun getRecentReviews(userId: Long, limit: Int): List<RecentReviewResult> {
        val reviewPage = codeReviewRepository.findByUserIdOrderByCreatedAtDesc(userId, 0, limit)
        
        return reviewPage.content.map { review ->
            RecentReviewResult(
                reviewId = review.id,
                problemId = review.problem.id,
                problemTitle = review.problem.title,
                createdAt = review.createdAt
            )
        }
    }
    
    override fun getUserDashboardInfo(userId: Long): DashboardUserResult? {
        val user = userRepository.findById(userId) ?: return null
        
        // 동기화 데이터가 있는지 확인
        val solvedCount = userSolvedProblemRepository.countByUserId(userId)
        val hasSyncedData = solvedCount > 0
        
        return DashboardUserResult(
            user = user,
            hasSyncedData = hasSyncedData,
            totalSolvedProblems = solvedCount
        )
    }
}