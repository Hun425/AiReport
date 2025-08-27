package com.algoroadmap.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "companies")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(name = "name", nullable = false)
    val name: String,
    
    @Column(name = "logo_url")
    val logoUrl: String? = null,
    
    @Column(name = "description")
    val description: String? = null,
    
    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val profileTags: List<CompanyProfileTag> = emptyList()
)