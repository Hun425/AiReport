package com.algoroadmap.domain.service

/**
 * solved.ac API 서비스 도메인 인터페이스
 */
interface SolvedAcService {
    suspend fun fetchUserData(handle: String): SolvedAcUserData?
    suspend fun fetchUserSolvedProblems(handle: String): List<SolvedAcProblemData>
}

/**
 * solved.ac 사용자 데이터 (도메인 모델)
 */
data class SolvedAcUserData(
    val handle: String,
    val profileImageUrl: String?,
    val solvedCount: Int,
    val solvedAcClass: Int,
    val rank: Int
)

/**
 * solved.ac 문제 데이터 (도메인 모델)
 */
data class SolvedAcProblemData(
    val problemId: Long,
    val title: String,
    val difficulty: String?,
    val tags: List<String>
)