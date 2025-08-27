package com.algoroadmap.domain.repository

import com.algoroadmap.domain.entity.Company
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CompanyRepository {
    fun save(company: Company): Company
    fun findById(id: Long): Company?
    fun findAll(): List<Company>
    fun findAll(pageable: Pageable): Page<Company>
}