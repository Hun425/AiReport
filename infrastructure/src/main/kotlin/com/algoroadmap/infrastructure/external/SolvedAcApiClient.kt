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
            val countPerPage = 100  // 최대 100개씩 가져오기
            var hasMorePages = true
            
            while (hasMorePages) {
                logger.debug("페이지 ${page} 조회 중: handle=$handle")
                
                val response = solvedAcWebClient
                    .get()
                    .uri("/search/problem?query=solved_by:{handle}&sort=id&direction=asc&page={page}&count={count}", 
                         handle, page, countPerPage)
                    .retrieve()
                    .awaitBody<SolvedAcProblemSearchResponse>()
                
                allProblems.addAll(response.items)
                
                // 가져온 항목이 요청한 count보다 적으면 마지막 페이지
                hasMorePages = response.items.size >= countPerPage
                page++
                
                logger.debug("페이지 ${page - 1} 완료: 이번 페이지 ${response.items.size}개, 총 누적 ${allProblems.size}개")
                
                // API 과부하 방지를 위한 잠시 대기 (선택사항)
                kotlinx.coroutines.delay(100)
            }
            
            logger.info("solved.ac 사용자 푼 문제 목록 조회 성공: handle=$handle, 총 문제 수=${allProblems.size}")
            allProblems
            
        } catch (e: Exception) {
            logger.error("solved.ac 사용자 푼 문제 목록 조회 실패: handle=$handle", e)
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