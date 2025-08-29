package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "google_id", unique = true)
    val googleId: String? = null,
    
    @Column(name = "email")
    val email: String? = null,
    
    @Column(name = "solved_ac_handle", unique = true)
    val solvedAcHandle: String = "",
    
    @Column(name = "profile_image_url")
    val profileImageUrl: String? = null,
    
    @Column(name = "solved_ac_class")
    val solvedAcClass: Int = 0,
    
    @Column(name = "solved_count")
    val solvedCount: Int = 0,
    
    @Column(name = "rank")
    val rank: Int = 0,
    
    @Column(name = "last_synced_at")
    val lastSyncedAt: LocalDateTime? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_plan")
    val subscriptionPlan: SubscriptionPlan = SubscriptionPlan.FREE,
    
    @Column(name = "daily_review_used")
    val dailyReviewUsed: Int = 0,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val roadmaps: List<Roadmap> = emptyList(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val solvedProblems: List<UserSolvedProblem> = emptyList(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val codeReviews: List<CodeReview> = emptyList()
) {
    fun getDailyReviewLimit(): Int = when (subscriptionPlan) {
        SubscriptionPlan.FREE -> 10
        SubscriptionPlan.PREMIUM -> 100
    }
    
    fun canRequestReview(): Boolean = dailyReviewUsed < getDailyReviewLimit()
    
    fun incrementDailyReviewUsed(): User = this.copy(
        dailyReviewUsed = this.dailyReviewUsed + 1,
        updatedAt = LocalDateTime.now()
    )
    
    /**
     * 활성화된 로드맵 조회
     */
    fun getActiveRoadmap(): Roadmap? = roadmaps.find { it.isActive }
    
    /**
     * 특정 문제를 해결했는지 확인
     */
    fun hasSolvedProblem(problemId: Long): Boolean {
        return solvedProblems.any { it.problem.id == problemId }
    }
}

enum class SubscriptionPlan {
    FREE, PREMIUM
}