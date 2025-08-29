package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "problems")
class Problem(
    @Id
    var id: Long = 0, // BOJ 문제 번호를 직접 사용
    
    @Column(name = "title", nullable = false)
    var title: String = "",
    
    @Column(name = "difficulty")
    var difficulty: String? = null, // "Bronze 5", "Silver 3", "Gold 2" 등
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "problem_tags", joinColumns = [JoinColumn(name = "problem_id")])
    @Column(name = "tag")
    var tags: MutableSet<String> = mutableSetOf() // "DP", "그래프 탐색", "구현" 등
) {
    // JPA용 기본 생성자
    constructor() : this(0, "", null, mutableSetOf())
    
    fun getBojUrl(): String = "https://www.acmicpc.net/problem/$id"
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Problem) return false
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id.hashCode()
    }
    
    override fun toString(): String {
        return "Problem(id=$id, title='$title', difficulty=$difficulty)"
    }
}