package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.CodeReview
import com.algoroadmap.domain.repository.CodeReviewRepository
import com.algoroadmap.domain.repository.CodeReviewPage
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class CodeReviewRepositoryImpl(
    private val codeReviewJpaRepository: CodeReviewJpaRepository
) : CodeReviewRepository {
    
    override fun save(codeReview: CodeReview): CodeReview = codeReviewJpaRepository.save(codeReview)
    
    override fun findById(id: Long): CodeReview? = codeReviewJpaRepository.findById(id).orElse(null)
    
    override fun findByUserIdOrderByCreatedAtDesc(userId: Long, page: Int, size: Int): CodeReviewPage {
        val pageable = PageRequest.of(page, size)
        val springPage = codeReviewJpaRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
        
        return CodeReviewPage(
            content = springPage.content,
            page = springPage.number,
            size = springPage.size,
            totalElements = springPage.totalElements,
            totalPages = springPage.totalPages,
            hasNext = springPage.hasNext(),
            hasPrevious = springPage.hasPrevious()
        )
    }
    
    override fun countByUserIdAndCreatedAtAfter(userId: Long, date: LocalDateTime): Int =
        codeReviewJpaRepository.countByUserIdAndCreatedAtAfter(userId, date)
}