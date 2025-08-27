package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Company
import com.algoroadmap.domain.repository.CompanyRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CompanyRepositoryImpl(
    private val companyJpaRepository: CompanyJpaRepository
) : CompanyRepository {
    
    override fun save(company: Company): Company = companyJpaRepository.save(company)
    
    override fun findById(id: Long): Company? = companyJpaRepository.findById(id).orElse(null)
    
    override fun findAll(): List<Company> = companyJpaRepository.findAll()
    
    override fun findAll(pageable: Pageable): Page<Company> = companyJpaRepository.findAll(pageable)
}