package com.algoroadmap.domain.entity

import com.algoroadmap.domain.vo.CompanyName
import jakarta.persistence.*

@Entity
@Table(name = "companies")
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    
    @Column(name = "name", nullable = false)
    var name: CompanyName,
    
    @Column(name = "logo_url")
    var logoUrl: String? = null,

    @Column(name = "description")
    var description: String? = null,

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profileTags: MutableList<CompanyProfileTag> = mutableListOf()
) {
    // JPA용 기본 생성자
    constructor() : this(
        id = 0,
        name = CompanyName("리트만용"),
        logoUrl = null,
        description = null,
        profileTags = mutableListOf()
    )

    /**
     * 가장 중요한 태그들 반환 (가중치 내림차순)
     */
    fun getMostImportantTags(limit: Int = 5): List<String> {
        return profileTags
            .sortedByDescending { it.weight }
            .take(limit)
            .map { it.tagName }
    }

    /**
     * 특정 태그의 가중치 조회
     */
    fun getTagWeight(tagName: String): Int {
        return profileTags.find { it.tagName == tagName }?.weight ?: 0
    }

    /**
     * 한국 회사인지 확인
     */
    fun isKoreanCompany(): Boolean = name.isKoreanCompany()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Company) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Company(id=$id, name=${name.value}, description=$description)"
    }
}