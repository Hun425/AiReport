package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.User

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByHandle(handle: String): User?
    fun existsByHandle(handle: String): Boolean
    fun isSyncInProgress(userId: Long): Boolean
}