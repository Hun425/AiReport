package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "roadmap_problems")
class RoadmapProblem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    var week: RoadmapWeek,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    var problem: Problem,

    @Column(name = "order_in_week", nullable = false)
    var orderInWeek: Int,

    @Column(name = "recommended_tag")
    var recommendedTag: String? = null,

    @Column(name = "difficulty_reason")
    var difficultyReason: String? = null
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        week = RoadmapWeek(),
        problem = Problem(),
        orderInWeek = 1,
        recommendedTag = null,
        difficultyReason = null
    )

    /**
     * 사용자가 이 문제를 해결했는지 확인
     */
    fun isSolvedByUser(user: User): Boolean {
        return user.solvedProblems.any { it.problem.id == problem.id }
    }

    /**
     * 이 문제에 대한 코드 리뷰가 있는지 확인
     */
    fun hasReviewByUser(user: User): Boolean {
        return user.codeReviews.any { it.problem.id == problem.id }
    }

    /**
     * 추천 태그가 있는지 확인
     */
    fun hasRecommendedTag(): Boolean = !recommendedTag.isNullOrBlank()

    /**
     * 난이도 설명이 있는지 확인
     */
    fun hasDifficultyReason(): Boolean = !difficultyReason.isNullOrBlank()

    /**
     * 문제의 BOJ URL 반환
     */
    fun getBojUrl(): String = problem.getBojUrl()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RoadmapProblem) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "RoadmapProblem(id=$id, problem=${problem.title}, order=$orderInWeek)"
    }
}