package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "problems")
data class Problem(
    @Id
    val id: Long, // BOJ 문제 번호를 직접 사용
    
    @Column(name = "title", nullable = false)
    val title: String,
    
    @Column(name = "difficulty")
    val difficulty: String? = null, // "Bronze 5", "Silver 3", "Gold 2" 등
    
    @ElementCollection
    @CollectionTable(name = "problem_tags", joinColumns = [JoinColumn(name = "problem_id")])
    @Column(name = "tag")
    val tags: Set<String> = emptySet() // "DP", "그래프 탐색", "구현" 등
) {
    fun getBojUrl(): String = "https://www.acmicpc.net/problem/$id"
}