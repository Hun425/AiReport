package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.Company

interface CompanyRepository {
    fun save(company: Company): Company
    fun findById(id: Long): Company?
    fun findAll(): List<Company>
    fun findAll(page: Int, size: Int): CompanyPage
}

/**
 * 도메인 계층의 페이징 결과 (Spring 의존성 제거)
 */
data class CompanyPage(
    val content: List<Company>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)