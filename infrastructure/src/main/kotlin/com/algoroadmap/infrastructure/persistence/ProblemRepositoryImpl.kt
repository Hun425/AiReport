package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Problem
import com.algoroadmap.domain.repository.ProblemRepository
import org.springframework.stereotype.Repository

@Repository
class ProblemRepositoryImpl(
    private val problemJpaRepository: ProblemJpaRepository
) : ProblemRepository {
    
    override fun save(problem: Problem): Problem = problemJpaRepository.save(problem)
    
    override fun findById(id: Long): Problem? = problemJpaRepository.findById(id).orElse(null)
    
    override fun findByTags(tags: List<String>): List<Problem> = problemJpaRepository.findByTagsIn(tags)
    
    override fun saveAll(problems: List<Problem>): List<Problem> = problemJpaRepository.saveAll(problems)
}