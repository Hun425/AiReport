package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_solved_problems")
data class UserSolvedProblem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem,
    
    @Column(name = "solved_at")
    val solvedAt: LocalDateTime,
    
    @Column(name = "language")
    val language: String? = null, // "Kotlin", "Java", "Python" 등
    
    @Column(name = "memory_kb")
    val memoryKb: Int? = null,
    
    @Column(name = "time_ms")
    val timeMs: Int? = null
) {
    // 복합 유니크 제약조건을 위한 초기화 블록
    init {
        // JPA에서는 @Table(uniqueConstraints = [...])로 처리하는 것이 더 좋지만
        // 여기서는 비즈니스 로직으로 표현
    }
}