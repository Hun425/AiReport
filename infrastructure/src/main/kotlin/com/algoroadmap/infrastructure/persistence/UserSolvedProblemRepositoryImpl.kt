package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.UserSolvedProblem
import com.algoroadmap.domain.repository.UserSolvedProblemRepository
import org.springframework.stereotype.Repository

@Repository
class UserSolvedProblemRepositoryImpl(
    private val userSolvedProblemJpaRepository: UserSolvedProblemJpaRepository
) : UserSolvedProblemRepository {
    
    override fun save(userSolvedProblem: UserSolvedProblem): UserSolvedProblem = 
        userSolvedProblemJpaRepository.save(userSolvedProblem)
    
    override fun saveAll(userSolvedProblems: List<UserSolvedProblem>): List<UserSolvedProblem> = 
        userSolvedProblemJpaRepository.saveAll(userSolvedProblems)
    
    override fun findByUserId(userId: Long): List<UserSolvedProblem> = 
        userSolvedProblemJpaRepository.findByUserId(userId)
    
    override fun findByUserIdAndProblemId(userId: Long, problemId: Long): UserSolvedProblem? = 
        userSolvedProblemJpaRepository.findByUserIdAndProblemId(userId, problemId)
    
    override fun countByUserId(userId: Long): Int = 
        userSolvedProblemJpaRepository.countByUserId(userId)
    
    override fun findTagAnalysByUserId(userId: Long): Map<String, Int> {
        val results = userSolvedProblemJpaRepository.findTagAnalysByUserId(userId)
        return results.associate { 
            it[0] as String to (it[1] as Long).toInt()
        }
    }
}