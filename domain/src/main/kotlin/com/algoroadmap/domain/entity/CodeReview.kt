package com.algoroadmap.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "code_reviews")
data class CodeReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem,
    
    @Column(name = "language", nullable = false)
    val language: String, // "Kotlin", "Java", "Python" 등
    
    @Lob
    @Column(name = "submitted_code", nullable = false)
    val submittedCode: String,
    
    @Lob
    @Column(name = "review_content", nullable = false)
    val reviewContent: String, // Markdown 형식의 AI 리뷰 내용
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)