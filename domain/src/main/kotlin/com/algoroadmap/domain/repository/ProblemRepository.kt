package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.Problem

interface ProblemRepository {
    fun save(problem: Problem): Problem
    fun findById(id: Long): Problem?
    fun findByTags(tags: List<String>): List<Problem>
    fun findByTagAndDifficulty(tag: String, difficultyLevel: String, limit: Int): List<Problem>
    fun saveAll(problems: List<Problem>): List<Problem>
}