package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Company
import com.algoroadmap.domain.repository.CompanyRepository
import com.algoroadmap.domain.repository.CompanyPage
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class CompanyRepositoryImpl(
    private val companyJpaRepository: CompanyJpaRepository
) : CompanyRepository {
    
    override fun save(company: Company): Company = companyJpaRepository.save(company)
    
    override fun findById(id: Long): Company? = companyJpaRepository.findById(id).orElse(null)
    
    override fun findAll(): List<Company> = companyJpaRepository.findAll()
    
    override fun findAll(page: Int, size: Int): CompanyPage {
        val pageable = PageRequest.of(page, size)
        val springPage = companyJpaRepository.findAll(pageable)
        
        return CompanyPage(
            content = springPage.content,
            page = springPage.number,
            size = springPage.size,
            totalElements = springPage.totalElements,
            totalPages = springPage.totalPages,
            hasNext = springPage.hasNext(),
            hasPrevious = springPage.hasPrevious()
        )
    }
}