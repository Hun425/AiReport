package com.algoroadmap.infrastructure.persistence

import com.algoroadmap.domain.entity.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyJpaRepository : JpaRepository<Company, Long>