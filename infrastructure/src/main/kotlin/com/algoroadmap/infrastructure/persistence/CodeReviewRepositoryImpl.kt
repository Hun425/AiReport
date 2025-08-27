package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.CodeReview
import com.algoroadmap.domain.repository.CodeReviewRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CodeReviewRepositoryImpl(
    private val codeReviewJpaRepository: CodeReviewJpaRepository
) : CodeReviewRepository {
    
    override fun save(codeReview: CodeReview): CodeReview = codeReviewJpaRepository.save(codeReview)
    
    override fun findById(id: Long): CodeReview? = codeReviewJpaRepository.findById(id).orElse(null)
    
    override fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<CodeReview> =
        codeReviewJpaRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
    
    override fun countByUserIdAndCreatedAtAfter(userId: Long, date: LocalDateTime): Int =
        codeReviewJpaRepository.countByUserIdAndCreatedAtAfter(userId, date)
}