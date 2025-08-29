package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.RoadmapWeek

interface RoadmapWeekRepository {
    fun save(roadmapWeek: RoadmapWeek): RoadmapWeek
    fun saveAll(roadmapWeeks: List<RoadmapWeek>): List<RoadmapWeek>
    fun findById(id: Long): RoadmapWeek?
    fun findByRoadmapId(roadmapId: Long): List<RoadmapWeek>
    fun findByRoadmapIdAndWeekNumber(roadmapId: Long, weekNumber: Int): RoadmapWeek?
    fun deleteByRoadmapId(roadmapId: Long)
}