package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.RoadmapWeek
import com.algoroadmap.domain.repository.RoadmapWeekRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RoadmapWeekRepositoryImpl(
    private val roadmapWeekJpaRepository: RoadmapWeekJpaRepository
) : RoadmapWeekRepository {
    
    override fun save(roadmapWeek: RoadmapWeek): RoadmapWeek = roadmapWeekJpaRepository.save(roadmapWeek)
    
    override fun saveAll(roadmapWeeks: List<RoadmapWeek>): List<RoadmapWeek> = 
        roadmapWeekJpaRepository.saveAll(roadmapWeeks)
    
    override fun findById(id: Long): RoadmapWeek? = roadmapWeekJpaRepository.findById(id).orElse(null)
    
    override fun findByRoadmapId(roadmapId: Long): List<RoadmapWeek> = 
        roadmapWeekJpaRepository.findByRoadmapIdOrderByWeekNumber(roadmapId)
    
    override fun findByRoadmapIdAndWeekNumber(roadmapId: Long, weekNumber: Int): RoadmapWeek? = 
        roadmapWeekJpaRepository.findByRoadmapIdAndWeekNumber(roadmapId, weekNumber)
    
    @Transactional
    override fun deleteByRoadmapId(roadmapId: Long) {
        roadmapWeekJpaRepository.deleteByRoadmapId(roadmapId)
    }
}