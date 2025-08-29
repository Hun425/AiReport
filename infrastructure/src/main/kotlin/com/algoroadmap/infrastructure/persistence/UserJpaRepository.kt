package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findBySolvedAcHandle(handle: String): User?
    fun findByGoogleId(googleId: String): User?
    fun existsBySolvedAcHandle(handle: String): Boolean
    
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId AND u.lastSyncedAt IS NULL")
    fun isSyncInProgress(@Param("userId") userId: Long): Boolean
}