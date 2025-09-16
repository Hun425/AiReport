package com.algoroadmap.domain.entity

import com.algoroadmap.domain.vo.ProblemDifficulty
import jakarta.persistence.*

@Entity
@Table(name = "problems")
class Problem(
    @Id
    var id: Long = 0, // BOJ 문제 번호를 직접 사용
    
    @Column(name = "title", nullable = false)
    var title: String = "",
    
    @Column(name = "difficulty")
    var difficulty: ProblemDifficulty? = null,
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "problem_tags", joinColumns = [JoinColumn(name = "problem_id")])
    @Column(name = "tag")
    var tags: MutableSet<String> = mutableSetOf() // "DP", "그래프 탐색", "구현" 등
) {
    // JPA용 기본 생성자
    constructor() : this(0, "", null, mutableSetOf())
    
    fun getBojUrl(): String = "https://www.acmicpc.net/problem/$id"

    /**
     * 문제가 쉬운지 확인 (Bronze, Silver)
     */
    fun isEasy(): Boolean = difficulty?.isEasy() ?: false

    /**
     * 문제가 어려운지 확인 (Platinum 이상)
     */
    fun isHard(): Boolean = difficulty?.isHard() ?: false

    /**
     * 특정 태그를 포함하는지 확인
     */
    fun hasTag(tagName: String): Boolean = tags.contains(tagName)

    /**
     * 주요 알고리즘 유형 태그들 필터링
     */
    fun getMainAlgorithmTags(): Set<String> {
        val mainTags = setOf(
            "DP", "그래프 탐색", "구현", "그리디", "문자열",
            "수학", "정렬", "이분 탐색", "자료 구조", "조합론"
        )
        return tags.filter { it in mainTags }.toSet()
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Problem) return false
        return id == other.id
    }
    
    override fun hashCode(): Int {
        return id.hashCode()
    }
    
    override fun toString(): String {
        return "Problem(id=$id, title='$title', difficulty=${difficulty?.value})"
    }
}