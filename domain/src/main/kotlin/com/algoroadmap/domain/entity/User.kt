package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    
    @Column(name = "google_id", unique = true)
    var googleId: String? = null,
    
    @Column(name = "email")
    var email: String? = null,
    
    @Column(name = "solved_ac_handle", unique = true)
    var solvedAcHandle: String = "",
    
    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null,
    
    @Column(name = "solved_ac_class")
    var solvedAcClass: Int = 0,
    
    @Column(name = "solved_count")
    var solvedCount: Int = 0,
    
    @Column(name = "rank")
    var rank: Int = 0,
    
    @Column(name = "last_synced_at")
    var lastSyncedAt: LocalDateTime? = null,
    
    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_plan")
    var subscriptionPlan: SubscriptionPlan = SubscriptionPlan.FREE,
    
    @Column(name = "daily_review_used")
    var dailyReviewUsed: Int = 0,
    
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var roadmaps: MutableList<Roadmap> = mutableListOf(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var solvedProblems: MutableList<UserSolvedProblem> = mutableListOf(),
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var codeReviews: MutableList<CodeReview> = mutableListOf()
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        googleId = null,
        email = null,
        solvedAcHandle = "",
        profileImageUrl = null,
        solvedAcClass = 0,
        solvedCount = 0,
        rank = 0,
        lastSyncedAt = null,
        subscriptionPlan = SubscriptionPlan.FREE,
        dailyReviewUsed = 0,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        roadmaps = mutableListOf(),
        solvedProblems = mutableListOf(),
        codeReviews = mutableListOf()
    )
    fun getDailyReviewLimit(): Int = when (subscriptionPlan) {
        SubscriptionPlan.FREE -> 10
        SubscriptionPlan.PREMIUM -> 100
    }
    
    fun canRequestReview(): Boolean = dailyReviewUsed < getDailyReviewLimit()
    
    fun incrementDailyReviewUsed() {
        this.dailyReviewUsed = this.dailyReviewUsed + 1
        this.updatedAt = LocalDateTime.now()
    }
    
    /**
     * 활성화된 로드맵 조회
     */
    fun getActiveRoadmap(): Roadmap? = roadmaps.find { it.isActive }
    
    /**
     * 특정 문제를 해결했는지 확인
     */
    fun hasSolvedProblem(problemId: Long): Boolean {
        return solvedProblems.any { solvedProblem ->
            try {
                solvedProblem.problem.id == problemId
            } catch (_: UninitializedPropertyAccessException) {
                false
            }
        }
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id.hashCode()
    }
    
    override fun toString(): String {
        return "User(id=$id, email=$email, solvedAcHandle='$solvedAcHandle')"
    }
}

enum class SubscriptionPlan {
    FREE, PREMIUM
}