package com.algoroadmap.infrastructure.external

import com.algoroadmap.domain.service.SolvedAcService
import com.algoroadmap.domain.service.SolvedAcUserData
import com.algoroadmap.domain.service.SolvedAcProblemData
import org.springframework.stereotype.Service

@Service
class SolvedAcServiceImpl(
    private val solvedAcApiClient: SolvedAcApiClient
) : SolvedAcService {
    
    override suspend fun fetchUserData(handle: String): SolvedAcUserData? {
        val apiUser = solvedAcApiClient.fetchUserData(handle) ?: return null
        
        return SolvedAcUserData(
            handle = apiUser.handle,
            profileImageUrl = apiUser.profileImageUrl,
            solvedCount = apiUser.solvedCount,
            solvedAcClass = apiUser.`class`,
            rank = apiUser.rank
        )
    }
    
    override suspend fun fetchUserSolvedProblems(handle: String): List<SolvedAcProblemData> {
        val apiProblems = solvedAcApiClient.fetchUserSolvedProblems(handle)
        
        return apiProblems.map { problem ->
            SolvedAcProblemData(
                problemId = problem.problemId,
                title = problem.titleKo,
                difficulty = mapLevelToDifficulty(problem.level),
                tags = problem.tags.flatMap { tag ->
                    tag.displayNames
                        .filter { it.language == "ko" }
                        .map { it.name }
                }
            )
        }
    }
    
    private fun mapLevelToDifficulty(level: Int): String? {
        return when (level) {
            in 1..5 -> "Bronze ${6 - level}"
            in 6..10 -> "Silver ${11 - level}"
            in 11..15 -> "Gold ${16 - level}"
            in 16..20 -> "Platinum ${21 - level}"
            in 21..25 -> "Diamond ${26 - level}"
            in 26..30 -> "Ruby ${31 - level}"
            else -> null
        }
    }
}