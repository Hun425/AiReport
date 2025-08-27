package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.UserSolvedProblem

interface UserSolvedProblemRepository {
    fun save(userSolvedProblem: UserSolvedProblem): UserSolvedProblem
    fun saveAll(userSolvedProblems: List<UserSolvedProblem>): List<UserSolvedProblem>
    fun findByUserId(userId: Long): List<UserSolvedProblem>
    fun findByUserIdAndProblemId(userId: Long, problemId: Long): UserSolvedProblem?
    fun countByUserId(userId: Long): Int
    fun findTagAnalysByUserId(userId: Long): Map<String, Int>
}