package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.CodeReview
import java.time.LocalDateTime

interface CodeReviewRepository {
    fun save(codeReview: CodeReview): CodeReview
    fun findById(id: Long): CodeReview?
    fun findByUserIdOrderByCreatedAtDesc(userId: Long, page: Int, size: Int): CodeReviewPage
    fun countByUserIdAndCreatedAtAfter(userId: Long, date: LocalDateTime): Int
}

/**
 * 도메인 계층의 코드 리뷰 페이징 결과 (Spring 의존성 제거)
 */
data class CodeReviewPage(
    val content: List<CodeReview>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)