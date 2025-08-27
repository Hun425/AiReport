package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "company_profile_tags")
data class CompanyProfileTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    val company: Company,
    
    @Column(name = "tag_name", nullable = false)
    val tagName: String, // "DP", "그래프 탐색", "구현" 등
    
    @Column(name = "weight")
    val weight: Int = 1 // 가중치 (중요도)
)