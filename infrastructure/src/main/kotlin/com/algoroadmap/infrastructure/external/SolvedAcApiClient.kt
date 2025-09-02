package com.algoroadmap.infrastructure.external


import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class SolvedAcApiClient(
    private val solvedAcWebClient: WebClient
) {
    private val logger = LoggerFactory.getLogger(SolvedAcApiClient::class.java)
    
    /**
     * solved.ac API로부터 사용자 정보를 조회
     */
    suspend fun fetchUserData(handle: String): SolvedAcUser? {
        return try {
            logger.info("solved.ac 사용자 정보 조회 시작: handle=$handle")
            val user = solvedAcWebClient
                .get()
                .uri("/user/show?handle={handle}", handle)
                .retrieve()
                .awaitBody<SolvedAcUser>()
            logger.info("solved.ac 사용자 정보 조회 성공: handle=$handle, solvedCount=${user.solvedCount}")
            user
        } catch (e: Exception) {
            logger.error("solved.ac 사용자 정보 조회 실패: handle=$handle", e)
            null
        }
    }
    
    /**
     * 사용자가 푼 문제 목록 조회 (페이징 처리로 모든 문제 가져오기)
     */
    suspend fun fetchUserSolvedProblems(handle: String): List<SolvedAcProblem> {
        return try {
            logger.info("solved.ac 사용자 푼 문제 목록 조회 시작: handle=$handle")
            
            val allProblems = mutableListOf<SolvedAcProblem>()
            var page = 1
            val countPerPage = 50  // solved.ac API 실제 제한에 맞게 50으로 설정
            var hasMorePages = true
            var consecutiveEmptyPages = 0
            val maxConsecutiveEmpty = 3  // 연속 빈 페이지 최대 허용 수
            
            while (hasMorePages && consecutiveEmptyPages < maxConsecutiveEmpty) {
                logger.info("페이지 ${page} 조회 중: handle=$handle")
                
                val response = solvedAcWebClient
                    .get()
                    .uri("/search/problem?query=solved_by:{handle}&sort=id&direction=asc&page={page}&count={count}", 
                         handle, page, countPerPage)
                    .retrieve()
                    .awaitBody<SolvedAcProblemSearchResponse>()
                
                logger.info("API 응답 정보 - 페이지: $page, response.count: ${response.count}, items.size: ${response.items.size}")
                
                if (response.items.isEmpty()) {
                    consecutiveEmptyPages++
                    logger.warn("빈 페이지 발견: $page (연속 빈 페이지: $consecutiveEmptyPages)")
                } else {
                    consecutiveEmptyPages = 0
                    allProblems.addAll(response.items)
                }
                
                // 종료 조건 개선: items가 비어있거나 countPerPage보다 적으면 종료
                hasMorePages = response.items.size == countPerPage
                page++
                
                logger.info("페이지 ${page - 1} 완료: 이번 페이지 ${response.items.size}개, 총 누적 ${allProblems.size}개, 다음 페이지 있음: $hasMorePages")
                
                // API 과부하 방지를 위한 대기 시간 증가
                kotlinx.coroutines.delay(200)
                
                // 안전장치: 너무 많은 페이지 방지 (최대 1000페이지)
                if (page > 1000) {
                    logger.warn("최대 페이지 수 도달로 종료: handle=$handle, page=$page")
                    break
                }
            }
            
            logger.info("solved.ac 사용자 푼 문제 목록 조회 완료: handle=$handle, 총 문제 수=${allProblems.size}, 마지막 페이지=${page - 1}")
            allProblems
            
        } catch (e: Exception) {
            logger.error("solved.ac 사용자 푼 문제 목록 조회 실패: handle=$handle", e)
            emptyList()
        }
    }
    
    /**
     * 대안 방법: 사용자 통계 API를 통한 문제 수 확인
     * solved.ac의 다른 엔드포인트를 시도해볼 수 있습니다
     */
    suspend fun fetchUserSolvedProblemsAlternative(handle: String): List<SolvedAcProblem> {
        return try {
            logger.info("대안 방법으로 사용자 푼 문제 목록 조회 시작: handle=$handle")
            
            val allProblems = mutableListOf<SolvedAcProblem>()
            
            // 방법 1: 다른 쿼리 방식 시도
            val response = solvedAcWebClient
                .get()
                .uri("/search/problem?query=@{handle}&sort=solved&direction=desc", handle)
                .retrieve()
                .awaitBody<SolvedAcProblemSearchResponse>()
                
            allProblems.addAll(response.items)
            logger.info("대안 방법 조회 결과: ${allProblems.size}개 문제 발견")
            
            allProblems
            
        } catch (e: Exception) {
            logger.error("대안 방법으로 문제 조회 실패: handle=$handle", e)
            emptyList()
        }
    }
}

/**
 * solved.ac API 사용자 정보 응답
 */
data class SolvedAcUser(
    val handle: String,
    val bio: String?,
    val badgeId: String?,
    val backgroundId: String?,
    val profileImageUrl: String?,
    val solvedCount: Int,
    val voteCount: Int,
    val exp: Long,
    val `class`: Int,
    val classDecoration: String?,
    val rivalCount: Int,
    val reverseRivalCount: Int,
    val tier: Int,
    val rating: Int,
    val ratingByProblemsSum: Int,
    val ratingByClass: Int,
    val ratingBySolvedCount: Int,
    val ratingByVoteCount: Int,
    val maxStreak: Int,
    val rank: Int,
    val isRival: Boolean,
    val isReverseRival: Boolean
)

/**
 * solved.ac API 문제 정보 응답
 */
data class SolvedAcProblem(
    val problemId: Long,
    val titleKo: String,
    val isSolvable: Boolean,
    val isPartial: Boolean,
    val acceptedUserCount: Int,
    val level: Int,
    val votedUserCount: Int,
    val sprout: Boolean,
    val givesNoRating: Boolean,
    val isLevelLocked: Boolean,
    val averageTries: Double,
    val tags: List<SolvedAcTag>
)

data class SolvedAcTag(
    val key: String,
    val isMeta: Boolean,
    val bojTagId: Int,
    val problemCount: Int,
    val displayNames: List<SolvedAcDisplayName>
)

data class SolvedAcDisplayName(
    val language: String,
    val name: String,
    val short: String?
)

data class SolvedAcProblemSearchResponse(
    val count: Int,
    val items: List<SolvedAcProblem>
)