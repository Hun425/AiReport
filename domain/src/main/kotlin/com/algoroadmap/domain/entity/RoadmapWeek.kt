package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "roadmap_weeks")
class RoadmapWeek(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    var roadmap: Roadmap,

    @Column(name = "week_number", nullable = false)
    var weekNumber: Int,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @OneToMany(mappedBy = "week", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var problems: MutableList<RoadmapProblem> = mutableListOf()
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        roadmap = Roadmap(),
        weekNumber = 1,
        title = "",
        description = null,
        problems = mutableListOf()
    )

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

    /**
     * 주차가 완료되었는지 확인 (모든 문제 해결)
     */
    fun isCompleted(): Boolean {
        return problems.isNotEmpty() && getSolvedProblemCount() == problems.size
    }

    /**
     * 남은 문제 수
     */
    fun getRemainingProblemCount(): Int {
        return problems.size - getSolvedProblemCount()
    }

    /**
     * 추천 학습 순서대로 문제 정렬
     */
    fun getProblemsInOrder(): List<RoadmapProblem> {
        return problems.sortedBy { it.orderInWeek }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RoadmapWeek) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "RoadmapWeek(id=$id, week=$weekNumber, title='$title', problems=${problems.size}개)"
    }
}