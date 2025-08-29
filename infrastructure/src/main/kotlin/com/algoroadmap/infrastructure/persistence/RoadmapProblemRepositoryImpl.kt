package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.RoadmapProblem
import com.algoroadmap.domain.repository.RoadmapProblemRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RoadmapProblemRepositoryImpl(
    private val roadmapProblemJpaRepository: RoadmapProblemJpaRepository
) : RoadmapProblemRepository {
    
    override fun save(roadmapProblem: RoadmapProblem): RoadmapProblem = 
        roadmapProblemJpaRepository.save(roadmapProblem)
    
    override fun saveAll(roadmapProblems: List<RoadmapProblem>): List<RoadmapProblem> = 
        roadmapProblemJpaRepository.saveAll(roadmapProblems)
    
    override fun findById(id: Long): RoadmapProblem? = 
        roadmapProblemJpaRepository.findById(id).orElse(null)
    
    override fun findByWeekId(weekId: Long): List<RoadmapProblem> = 
        roadmapProblemJpaRepository.findByWeekIdOrderByOrderInWeek(weekId)
    
    override fun findByRoadmapId(roadmapId: Long): List<RoadmapProblem> = 
        roadmapProblemJpaRepository.findByRoadmapIdOrderByWeekAndOrder(roadmapId)
    
    @Transactional
    override fun deleteByWeekId(weekId: Long) {
        roadmapProblemJpaRepository.deleteByWeekId(weekId)
    }
}