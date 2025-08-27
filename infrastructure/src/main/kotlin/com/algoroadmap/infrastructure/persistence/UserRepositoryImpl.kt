package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.User
import com.algoroadmap.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {
    
    override fun save(user: User): User = userJpaRepository.save(user)
    
    override fun findById(id: Long): User? = userJpaRepository.findById(id).orElse(null)
    
    override fun findByHandle(handle: String): User? = userJpaRepository.findBySolvedAcHandle(handle)
    
    override fun existsByHandle(handle: String): Boolean = userJpaRepository.existsBySolvedAcHandle(handle)
    
    override fun isSyncInProgress(userId: Long): Boolean = userJpaRepository.isSyncInProgress(userId)
}