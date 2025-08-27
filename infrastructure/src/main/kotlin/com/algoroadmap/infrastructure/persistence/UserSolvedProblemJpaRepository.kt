package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.UserSolvedProblem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserSolvedProblemJpaRepository : JpaRepository<UserSolvedProblem, Long> {
    
    fun findByUserId(userId: Long): List<UserSolvedProblem>
    
    fun findByUserIdAndProblemId(userId: Long, problemId: Long): UserSolvedProblem?
    
    fun countByUserId(userId: Long): Int
    
    @Query("""
        SELECT tag, COUNT(*) as count
        FROM UserSolvedProblem usp 
        JOIN usp.problem p 
        JOIN p.tags tag 
        WHERE usp.user.id = :userId
        GROUP BY tag
    """)
    fun findTagAnalysByUserId(@Param("userId") userId: Long): List<Array<Any>>
}