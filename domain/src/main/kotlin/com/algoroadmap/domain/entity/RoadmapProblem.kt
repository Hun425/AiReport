package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "roadmap_problems")
data class RoadmapProblem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    val week: RoadmapWeek,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem,
    
    @Column(name = "order_in_week", nullable = false)
    val orderInWeek: Int,
    
    @Column(name = "recommended_tag")
    val recommendedTag: String? = null,
    
    @Column(name = "difficulty_reason")
    val difficultyReason: String? = null
) {
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
}