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
    private val codeReviewRepository: CodeReviewRepository,
    private val problemRepository: com.algoroadmap.domain.repository.ProblemRepository
) : DashboardService {
    
    override fun getUserTagAnalysis(userId: Long): Map<String, TagAnalysisResult> {
        val logger = org.slf4j.LoggerFactory.getLogger(DashboardServiceImpl::class.java)
        
        // 사용자가 푼 문제의 태그별 개수 조회
        val rawUserTagCounts = userSolvedProblemRepository.findTagAnalysByUserId(userId)
        logger.info("사용자 태그 분석 시작: userId=$userId, 원본 태그 수=${rawUserTagCounts.size}")
        logger.debug("원본 사용자 태그 데이터: $rawUserTagCounts")
        
        // 태그 매핑 및 그룹핑 처리
        val processedTagCounts = processTagsForDisplay(rawUserTagCounts)
        logger.info("태그 처리 완료: 표시용 태그 수=${processedTagCounts.size}")
        logger.debug("처리된 사용자 태그 데이터: $processedTagCounts")
        
        // 실제 DB 데이터 기반으로 태그별 전체 문제 수 조회
        val realTagTotalProblems = try {
            val rawTagTotals = problemRepository.countAllByTag()
            logger.info("DB에서 태그별 전체 문제 수 조회 성공: ${rawTagTotals.size}개 태그")
            logger.debug("원본 전체 태그 데이터: $rawTagTotals")
            
            val processed = processTagTotalsForDisplay(rawTagTotals)
            logger.info("전체 문제 수 처리 완료: ${processed.size}개 표시용 태그")
            logger.debug("처리된 전체 태그 데이터: $processed")
            processed
        } catch (e: Exception) {
            // DB 조회 실패 시 기본값 사용
            logger.warn("태그별 전체 문제 수 조회 실패, 기본값 사용: ${e.message}")
            val defaults = getDefaultTagTotalProblems()
            logger.info("기본값 사용: ${defaults.size}개 태그")
            defaults
        }
        
        // 최종 결과 생성 및 로깅
        val finalResults = processedTagCounts.mapValues { (tag, solvedCount) ->
            val totalInTag = realTagTotalProblems[tag] ?: run {
                // 실제 데이터에 없는 태그의 경우 추정값 계산
                val averageTotalPerTag = if (realTagTotalProblems.isNotEmpty()) {
                    realTagTotalProblems.values.average().toInt()
                } else {
                    50
                }
                logger.debug("태그 '$tag'의 전체 문제 수를 찾을 수 없어 평균값 사용: $averageTotalPerTag")
                averageTotalPerTag
            }
            
            val percentage = if (totalInTag > 0) (solvedCount.toDouble() / totalInTag * 100) else 0.0
            logger.debug("태그 '$tag': 푼 문제=${solvedCount}, 전체=${totalInTag}, 퍼센트=${String.format("%.1f", percentage)}%")
            
            TagAnalysisResult(
                tag = tag,
                solvedCount = solvedCount,
                totalProblemsInTag = totalInTag
            )
        }
        
        logger.info("태그 분석 완료: ${finalResults.size}개 태그 처리됨")
        return finalResults
    }
    
    /**
     * 실제 DB 태그 데이터를 표시용으로 그룹핑 (사용자 태그와 동일한 로직)
     */
    private fun processTagTotalsForDisplay(rawTagTotals: Map<String, Int>): Map<String, Int> {
        val normalizedTotals = mutableMapOf<String, Int>()
        
        rawTagTotals.forEach { (originalTag, count) ->
            val mappedTag = mapToDisplayTag(originalTag)
            normalizedTotals[mappedTag] = (normalizedTotals[mappedTag] ?: 0) + count
        }
        
        return normalizedTotals
    }
    
    /**
     * 기본 태그별 전체 문제 수 (DB 조회 실패 시 사용)
     */
    private fun getDefaultTagTotalProblems(): Map<String, Int> = mapOf(
        "DP" to 100,
        "그래프 탐색" to 100, 
        "구현" to 120,
        "그리디" to 60,
        "문자열" to 80,
        "수학" to 90,
        "정렬" to 40,
        "이분탐색" to 50,
        "브루트포스" to 70,
        "자료구조" to 85,
        "트리" to 65,
        "누적합" to 30,
        "투 포인터" to 25,
        "백트래킹" to 40,
        "조합론" to 35,
        "기타" to 200
    )
    
    /**
     * 원형 그래프 표시용 태그 처리
     * - 상위 8개 태그만 표시
     * - 나머지는 "기타"로 그룹핑
     * - 코딩테스트 주요 태그로 매핑
     */
    private fun processTagsForDisplay(rawTagCounts: Map<String, Int>, maxDisplayTags: Int = 8): Map<String, Int> {
        // 1단계: 태그 정규화 (solved.ac 태그 → 표시용 태그 매핑)
        val normalizedTags = mutableMapOf<String, Int>()
        
        rawTagCounts.forEach { (originalTag, count) ->
            val mappedTag = mapToDisplayTag(originalTag)
            normalizedTags[mappedTag] = (normalizedTags[mappedTag] ?: 0) + count
        }
        
        // 2단계: 상위 N개 선택 (푼 문제 수 기준)
        val sortedTags = normalizedTags.toList().sortedByDescending { it.second }
        val topTags = sortedTags.take(maxDisplayTags).toMap().toMutableMap()
        
        // 3단계: 나머지 태그들을 "기타"로 통합
        val remainingTags = sortedTags.drop(maxDisplayTags)
        if (remainingTags.isNotEmpty()) {
            val otherCount = remainingTags.sumOf { it.second }
            topTags["기타"] = otherCount
        }
        
        return topTags
    }
    
    /**
     * solved.ac 태그를 표시용 태그로 매핑
     */
    private fun mapToDisplayTag(originalTag: String): String {
        return when (originalTag.lowercase()) {
            "dp", "dynamic_programming", "다이나믹 프로그래밍" -> "DP"
            "graphs", "graph_traversal", "그래프 이론", "그래프 탐색", "dfs", "bfs" -> "그래프 탐색"
            "implementation", "구현", "시뮬레이션", "simulation" -> "구현"
            "greedy", "그리디 알고리즘", "그리디" -> "그리디"
            "string", "strings", "문자열" -> "문자열"
            "math", "mathematics", "number_theory", "수학", "정수론" -> "수학"
            "sorting", "정렬" -> "정렬"
            "binary_search", "이분 탐색", "이분탐색" -> "이분탐색"
            "brute_force", "bruteforcing", "브루트포스" -> "브루트포스"
            "data_structures", "자료 구조", "자료구조" -> "자료구조"
            "tree", "trees", "트리" -> "트리"
            "prefix_sum", "누적 합", "누적합" -> "누적합"
            "two_pointer", "투 포인터", "투포인터" -> "투 포인터"
            "backtracking", "백트래킹" -> "백트래킹"
            "combinatorics", "조합론" -> "조합론"
            else -> originalTag // 매핑되지 않는 태그는 원본 유지
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