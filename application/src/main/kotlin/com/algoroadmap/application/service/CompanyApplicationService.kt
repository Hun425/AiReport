package com.algoroadmap.application.service

import com.algoroadmap.application.dto.CompanyInfo
import com.algoroadmap.application.dto.CompanyListResponse
import com.algoroadmap.application.dto.PaginationInfo
import com.algoroadmap.domain.repository.CompanyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 기업 관련 Application Service
 */
@Service
@Transactional(readOnly = true)
class CompanyApplicationService(
    private val companyRepository: CompanyRepository
) {
    
    /**
     * 모든 기업 목록 조회 (페이징 없음)
     */
    fun getAllCompanies(): List<CompanyInfo> {
        return companyRepository.findAll()
            .map { CompanyInfo.from(it) }
    }
    
    /**
     * 기업 목록 조회 (페이징)
     */
    fun getCompanies(page: Int, size: Int): CompanyListResponse {
        // 페이지는 1-based이지만 Repository는 0-based를 사용
        val adjustedPage = maxOf(0, page - 1)
        val adjustedSize = when {
            size <= 0 -> 20  // 기본값
            size > 100 -> 100 // 최대값
            else -> size
        }
        
        val companyPage = companyRepository.findAll(adjustedPage, adjustedSize)
        val companyInfos = companyPage.content.map { CompanyInfo.from(it) }
        
        return CompanyListResponse(
            data = companyInfos,
            pagination = PaginationInfo.from(companyPage)
        )
    }
    
    /**
     * 특정 기업 조회
     */
    fun getCompanyById(id: Long): CompanyInfo? {
        return companyRepository.findById(id)?.let { CompanyInfo.from(it) }
    }
}