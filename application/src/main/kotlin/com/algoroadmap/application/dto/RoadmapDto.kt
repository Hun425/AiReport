package com.algoroadmap.application.dto

import com.algoroadmap.domain.entity.*
import java.time.LocalDateTime

/**
 * 로드맵 생성 요청 DTO
 */
data class CreateRoadmapRequest(
    val companyId: Long,
    val durationInMonths: Int
)

/**
 * 로드맵 생성 응답 DTO
 */
data class CreateRoadmapResponse(
    val roadmapId: Long,
    val message: String
)

/**
 * 로드맵 상세 조회 응답 DTO
 */
data class RoadmapDetailResponse(
    val roadmapId: Long,
    val companyName: String,
    val durationInMonths: Int,
    val totalProgress: Double,
    val createdAt: LocalDateTime,
    val weeks: List<RoadmapWeekInfo>
)

/**
 * 로드맵 주차 정보 DTO
 */
data class RoadmapWeekInfo(
    val weekNumber: Int,
    val title: String,
    val progress: Double,
    val totalProblems: Int,
    val solvedProblems: Int,
    val problems: List<RoadmapProblemInfo>
)

/**
 * 로드맵 문제 정보 DTO
 */
data class RoadmapProblemInfo(
    val problemId: Long,
    val title: String,
    val difficulty: String,
    val isSolved: Boolean,
    val hasReview: Boolean,
    val bojUrl: String
) {
    companion object {
        fun from(roadmapProblem: RoadmapProblem, user: User): RoadmapProblemInfo {
            val problem = roadmapProblem.problem
            return RoadmapProblemInfo(
                problemId = problem.id,
                title = problem.title,
                difficulty = problem.difficulty?.value ?: "Unknown",
                isSolved = roadmapProblem.isSolvedByUser(user),
                hasReview = roadmapProblem.hasReviewByUser(user),
                bojUrl = "https://www.acmicpc.net/problem/${problem.id}"
            )
        }
    }
}

/**
 * 확장 함수: Roadmap -> RoadmapDetailResponse 변환
 */
fun Roadmap.toDetailResponse(): RoadmapDetailResponse {
    return RoadmapDetailResponse(
        roadmapId = this.id,
        companyName = this.company.name.value,
        durationInMonths = this.durationInMonths,
        totalProgress = this.calculateTotalProgress(),
        createdAt = this.createdAt,
        weeks = this.weeks.map { it.toWeekInfo(this.user) }
    )
}

/**
 * 확장 함수: RoadmapWeek -> RoadmapWeekInfo 변환
 */
fun RoadmapWeek.toWeekInfo(user: User): RoadmapWeekInfo {
    return RoadmapWeekInfo(
        weekNumber = this.weekNumber,
        title = this.title,
        progress = this.calculateProgress(),
        totalProblems = this.problems.size,
        solvedProblems = this.getSolvedProblemCount(),
        problems = this.problems.map { RoadmapProblemInfo.from(it, user) }
    )
}