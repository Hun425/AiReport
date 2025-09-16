package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "company_profile_tags")
class CompanyProfileTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company,

    @Column(name = "tag_name", nullable = false)
    var tagName: String, // "DP", "그래프 탐색", "구현" 등

    @Column(name = "weight")
    var weight: Int = 1 // 가중치 (중요도)
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        company = Company(),
        tagName = "",
        weight = 1
    )

    /**
     * 높은 가중치를 가진 중요한 태그인지 확인
     */
    fun isImportantTag(): Boolean = weight >= 5

    /**
     * 알고리즘 유형 태그인지 확인
     */
    fun isAlgorithmTag(): Boolean {
        val algorithmTags = setOf(
            "DP", "그래프 탐색", "구현", "그리디", "문자열",
            "수학", "정렬", "이분 탐색", "자료 구조", "조합론"
        )
        return tagName in algorithmTags
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CompanyProfileTag) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "CompanyProfileTag(company=${company.name.value}, tag=$tagName, weight=$weight)"
    }
}