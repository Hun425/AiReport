package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "roadmaps")
class Roadmap(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company,

    @Column(name = "duration_in_months", nullable = false)
    var durationInMonths: Int,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "completed_at")
    var completedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "roadmap", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var weeks: MutableList<RoadmapWeek> = mutableListOf()
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        user = User(),
        company = Company(),
        durationInMonths = 3,
        isActive = true,
        createdAt = LocalDateTime.now(),
        completedAt = null,
        weeks = mutableListOf()
    )

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

    /**
     * 로드맵이 완료되었는지 확인
     */
    fun isCompleted(): Boolean = completedAt != null

    /**
     * 로드맵 비활성화
     */
    fun deactivate() {
        this.isActive = false
    }

    /**
     * 로드맵 완료 처리
     */
    fun complete() {
        this.completedAt = LocalDateTime.now()
        this.isActive = false
    }

    /**
     * 특정 주차 조회
     */
    fun getWeek(weekNumber: Int): RoadmapWeek? {
        return weeks.find { it.weekNumber == weekNumber }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Roadmap) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Roadmap(id=$id, company=${company.name.value}, duration=${durationInMonths}개월, active=$isActive)"
    }
}