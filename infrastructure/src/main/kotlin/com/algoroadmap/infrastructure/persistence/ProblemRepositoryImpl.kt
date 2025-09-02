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
    
    override fun findByTagAndDifficulty(tag: String, difficultyLevel: String, limit: Int): List<Problem> {
        // 임시 구현 - 실제로는 태그와 난이도를 기반으로 조회해야 함
        return problemJpaRepository.findAll().take(limit)
    }
    
    override fun saveAll(problems: List<Problem>): List<Problem> = problemJpaRepository.saveAll(problems)
    
    override fun countAllByTag(): Map<String, Int> {
        val results = problemJpaRepository.countAllByTag()
        return results.associate { 
            it[0] as String to (it[1] as Long).toInt()
        }
    }
    
    override fun countAll(): Long = problemJpaRepository.count()
}