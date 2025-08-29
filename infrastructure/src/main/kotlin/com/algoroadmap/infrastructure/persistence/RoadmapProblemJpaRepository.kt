package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.RoadmapProblem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoadmapProblemJpaRepository : JpaRepository<RoadmapProblem, Long> {
    
    fun findByWeekIdOrderByOrderInWeek(weekId: Long): List<RoadmapProblem>
    
    @Query("""
        SELECT rp FROM RoadmapProblem rp 
        WHERE rp.week.roadmap.id = :roadmapId 
        ORDER BY rp.week.weekNumber, rp.orderInWeek
    """)
    fun findByRoadmapIdOrderByWeekAndOrder(roadmapId: Long): List<RoadmapProblem>
    
    fun deleteByWeekId(weekId: Long)
}