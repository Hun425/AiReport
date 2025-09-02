package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.Problem

interface ProblemRepository {
    fun save(problem: Problem): Problem
    fun findById(id: Long): Problem?
    fun findByTags(tags: List<String>): List<Problem>
    fun findByTagAndDifficulty(tag: String, difficultyLevel: String, limit: Int): List<Problem>
    fun saveAll(problems: List<Problem>): List<Problem>
    
    /**
     * 태그별 전체 문제 수 조회 (퍼센트 계산용)
     */
    fun countAllByTag(): Map<String, Int>
    
    /**
     * 전체 문제 수 조회
     */
    fun countAll(): Long
}