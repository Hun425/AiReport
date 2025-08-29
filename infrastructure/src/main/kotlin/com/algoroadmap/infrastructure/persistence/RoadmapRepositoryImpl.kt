package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Roadmap
import com.algoroadmap.domain.repository.RoadmapRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RoadmapRepositoryImpl(
    private val roadmapJpaRepository: RoadmapJpaRepository
) : RoadmapRepository {
    
    override fun save(roadmap: Roadmap): Roadmap = roadmapJpaRepository.save(roadmap)
    
    override fun findById(id: Long): Roadmap? = roadmapJpaRepository.findById(id).orElse(null)
    
    override fun findByUserId(userId: Long): List<Roadmap> = roadmapJpaRepository.findByUserId(userId)
    
    override fun findActiveByUserId(userId: Long): Roadmap? = 
        roadmapJpaRepository.findByUserIdAndIsActiveTrue(userId)
    
    override fun findByUserIdAndCompanyId(userId: Long, companyId: Long): Roadmap? = 
        roadmapJpaRepository.findByUserIdAndCompanyId(userId, companyId)
    
    override fun existsActiveRoadmapByUserId(userId: Long): Boolean = 
        roadmapJpaRepository.existsByUserIdAndIsActiveTrue(userId)
    
    @Transactional
    override fun deactivateAllByUserId(userId: Long) {
        roadmapJpaRepository.deactivateAllByUserId(userId)
    }
}