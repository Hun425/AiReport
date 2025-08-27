package com.algoroadmap.infrastructure.external


import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class SolvedAcApiClient(
    private val solvedAcWebClient: WebClient
) {
    
    /**
     * solved.ac API로부터 사용자 정보를 조회
     */
    suspend fun fetchUserData(handle: String): SolvedAcUser? {
        return try {
            solvedAcWebClient
                .get()
                .uri("/user/show?handle={handle}", handle)
                .retrieve()
                .awaitBody<SolvedAcUser>()
        } catch (e: Exception) {
            // 로깅 추가 필요
            null
        }
    }
    
    /**
     * 사용자가 푼 문제 목록 조회
     */
    suspend fun fetchUserSolvedProblems(handle: String): List<SolvedAcProblem> {
        return try {
            solvedAcWebClient
                .get()
                .uri("/search/problem?query=solved_by:{handle}&sort=id&direction=asc", handle)
                .retrieve()
                .awaitBody<SolvedAcProblemSearchResponse>()
                .items
        } catch (e: Exception) {
            // 로깅 추가 필요
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