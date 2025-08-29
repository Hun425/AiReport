package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.RoadmapWeek
import org.springframework.data.jpa.repository.JpaRepository

interface RoadmapWeekJpaRepository : JpaRepository<RoadmapWeek, Long> {
    
    fun findByRoadmapIdOrderByWeekNumber(roadmapId: Long): List<RoadmapWeek>
    
    fun findByRoadmapIdAndWeekNumber(roadmapId: Long, weekNumber: Int): RoadmapWeek?
    
    fun deleteByRoadmapId(roadmapId: Long)
}