package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "code_reviews")
class CodeReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    var problem: Problem,

    @Column(name = "language", nullable = false)
    var language: String, // "Kotlin", "Java", "Python" 등

    @Lob
    @Column(name = "submitted_code", nullable = false)
    var submittedCode: String,

    @Lob
    @Column(name = "review_content", nullable = false)
    var reviewContent: String, // Markdown 형식의 AI 리뷰 내용

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        user = User(),
        problem = Problem(),
        language = "",
        submittedCode = "",
        reviewContent = "",
        createdAt = LocalDateTime.now()
    )

    /**
     * 지원되는 프로그래밍 언어인지 확인
     */
    fun isSupportedLanguage(): Boolean {
        val supportedLanguages = setOf("Kotlin", "Java", "Python", "C++", "JavaScript")
        return language in supportedLanguages
    }

    /**
     * 리뷰 내용에 개선 사항이 있는지 확인
     */
    fun hasImprovementSuggestions(): Boolean {
        val improvementKeywords = listOf("개선", "수정", "최적화", "리팩토링")
        return improvementKeywords.any { reviewContent.contains(it) }
    }

    /**
     * 짧은 리뷰 요약 반환
     */
    fun getShortSummary(maxLength: Int = 100): String {
        return if (reviewContent.length <= maxLength) {
            reviewContent
        } else {
            reviewContent.take(maxLength) + "..."
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CodeReview) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "CodeReview(id=$id, problem=${problem.title}, language=$language)"
    }
}