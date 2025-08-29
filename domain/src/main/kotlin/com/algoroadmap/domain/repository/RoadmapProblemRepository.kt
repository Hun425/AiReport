package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.RoadmapProblem

interface RoadmapProblemRepository {
    fun save(roadmapProblem: RoadmapProblem): RoadmapProblem
    fun saveAll(roadmapProblems: List<RoadmapProblem>): List<RoadmapProblem>
    fun findById(id: Long): RoadmapProblem?
    fun findByWeekId(weekId: Long): List<RoadmapProblem>
    fun findByRoadmapId(roadmapId: Long): List<RoadmapProblem>
    fun deleteByWeekId(weekId: Long)
}