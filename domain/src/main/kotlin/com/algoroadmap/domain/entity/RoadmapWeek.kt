package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "roadmap_weeks")
data class RoadmapWeek(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    val roadmap: Roadmap,
    
    @Column(name = "week_number", nullable = false)
    val weekNumber: Int,
    
    @Column(name = "title", nullable = false)
    val title: String,
    
    @Column(name = "description")
    val description: String? = null,
    
    @OneToMany(mappedBy = "week", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val problems: List<RoadmapProblem> = emptyList()
) {
    /**
     * 이 주차의 진행률 계산 (%)
     */
    fun calculateProgress(): Double {
        if (problems.isEmpty()) return 0.0
        
        val solvedCount = problems.count { problemWeek ->
            roadmap.user.solvedProblems.any { it.problem.id == problemWeek.problem.id }
        }
        
        return (solvedCount.toDouble() / problems.size) * 100
    }
    
    /**
     * 완료한 문제 수
     */
    fun getSolvedProblemCount(): Int {
        return problems.count { problemWeek ->
            roadmap.user.solvedProblems.any { it.problem.id == problemWeek.problem.id }
        }
    }
}