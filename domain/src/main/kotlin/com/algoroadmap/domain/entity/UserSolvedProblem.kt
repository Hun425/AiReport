package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_solved_problems")
class UserSolvedProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    lateinit var problem: Problem
    
    @Column(name = "solved_at")
    var solvedAt: LocalDateTime = LocalDateTime.now()
    
    @Column(name = "language")
    var language: String? = null // "Kotlin", "Java", "Python" 등
    
    @Column(name = "memory_kb")
    var memoryKb: Int? = null
    
    @Column(name = "time_ms")
    var timeMs: Int? = null
    
    // JPA용 기본 생성자
    constructor()
    
    // 편의 생성자
    constructor(
        user: User,
        problem: Problem,
        solvedAt: LocalDateTime = LocalDateTime.now(),
        language: String? = null,
        memoryKb: Int? = null,
        timeMs: Int? = null
    ) : this() {
        this.user = user
        this.problem = problem
        this.solvedAt = solvedAt
        this.language = language
        this.memoryKb = memoryKb
        this.timeMs = timeMs
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserSolvedProblem) return false
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id.hashCode()
    }
    
    /**
     * 해결 언어가 설정되어 있는지 확인
     */
    fun hasLanguageInfo(): Boolean = !language.isNullOrBlank()

    /**
     * 성능 정보가 있는지 확인
     */
    fun hasPerformanceInfo(): Boolean = memoryKb != null && timeMs != null

    /**
     * 좋은 성능으로 해결했는지 확인 (메모리 128MB 이하, 시간 1초 이하)
     */
    fun hasGoodPerformance(): Boolean {
        return (memoryKb ?: Int.MAX_VALUE) <= 128 * 1024 && (timeMs ?: Int.MAX_VALUE) <= 1000
    }

    override fun toString(): String {
        return "UserSolvedProblem(id=$id, userId=${if(::user.isInitialized) user.id else "uninitialized"}, problemId=${if(::problem.isInitialized) problem.id else "uninitialized"})"
    }
}