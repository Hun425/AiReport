package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Problem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProblemJpaRepository : JpaRepository<Problem, Long> {
    
    @Query("""
        SELECT DISTINCT p FROM Problem p 
        JOIN p.tags t 
        WHERE t IN :tags
    """)
    fun findByTagsIn(@Param("tags") tags: List<String>): List<Problem>
}