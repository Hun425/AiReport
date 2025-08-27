package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.CodeReview
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface CodeReviewJpaRepository : JpaRepository<CodeReview, Long> {
    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<CodeReview>
    fun countByUserIdAndCreatedAtAfter(userId: Long, date: LocalDateTime): Int
}