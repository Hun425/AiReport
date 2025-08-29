package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "roadmaps")
data class Roadmap(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company,
    
    @Column(name = "duration_in_months", nullable = false)
    val durationInMonths: Int,
    
    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "completed_at")
    val completedAt: LocalDateTime? = null,
    
    @OneToMany(mappedBy = "roadmap", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val weeks: List<RoadmapWeek> = emptyList()
) {
    /**
     * 전체 진행률 계산 (%)
     */
    fun calculateTotalProgress(): Double {
        if (weeks.isEmpty()) return 0.0
        return weeks.map { it.calculateProgress() }.average()
    }
    
    /**
     * 총 문제 수
     */
    fun getTotalProblemCount(): Int {
        return weeks.sumOf { it.problems.size }
    }
    
    /**
     * 완료한 문제 수 (사용자가 해결한 문제)
     */
    fun getSolvedProblemCount(): Int {
        return weeks.sumOf { week ->
            week.problems.count { problemWeek ->
                user.solvedProblems.any { it.problem.id == problemWeek.problem.id }
            }
        }
    }
}