package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.CodeReview
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CodeReviewRepository {
    fun save(codeReview: CodeReview): CodeReview
    fun findById(id: Long): CodeReview?
    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<CodeReview>
    fun countByUserIdAndCreatedAtAfter(userId: Long, date: java.time.LocalDateTime): Int
}