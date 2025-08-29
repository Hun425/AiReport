package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.Roadmap

interface RoadmapRepository {
    fun save(roadmap: Roadmap): Roadmap
    fun findById(id: Long): Roadmap?
    fun findByUserId(userId: Long): List<Roadmap>
    fun findActiveByUserId(userId: Long): Roadmap?
    fun findByUserIdAndCompanyId(userId: Long, companyId: Long): Roadmap?
    fun existsActiveRoadmapByUserId(userId: Long): Boolean
    fun deactivateAllByUserId(userId: Long)
}