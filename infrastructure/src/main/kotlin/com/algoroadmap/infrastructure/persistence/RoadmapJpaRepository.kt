package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Roadmap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface RoadmapJpaRepository : JpaRepository<Roadmap, Long> {
    
    fun findByUserId(userId: Long): List<Roadmap>
    
    fun findByUserIdAndIsActiveTrue(userId: Long): Roadmap?
    
    fun findByUserIdAndCompanyId(userId: Long, companyId: Long): Roadmap?
    
    fun existsByUserIdAndIsActiveTrue(userId: Long): Boolean
    
    @Modifying
    @Query("UPDATE Roadmap r SET r.isActive = false WHERE r.user.id = :userId")
    fun deactivateAllByUserId(userId: Long)
}