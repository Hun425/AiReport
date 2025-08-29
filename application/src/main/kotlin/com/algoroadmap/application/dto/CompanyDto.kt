package com.algoroadmap.application.dto

import com.algoroadmap.domain.entity.Company
import com.algoroadmap.domain.repository.CompanyPage

/**
 * 기업 목록 조회 응답 DTO
 */
data class CompanyListResponse(
    val data: List<CompanyInfo>,
    val pagination: PaginationInfo
)

/**
 * 기업 정보 DTO
 */
data class CompanyInfo(
    val id: Long,
    val name: String,
    val logoUrl: String?,
    val description: String?
) {
    companion object {
        fun from(company: Company): CompanyInfo {
            return CompanyInfo(
                id = company.id,
                name = company.name,
                logoUrl = company.logoUrl,
                description = company.description
            )
        }
    }
}

/**
 * 페이지네이션 정보 DTO
 */
data class PaginationInfo(
    val page: Int,
    val size: Int,
    val total: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
) {
    companion object {
        fun from(companyPage: CompanyPage): PaginationInfo {
            return PaginationInfo(
                page = companyPage.page + 1, // 0-based를 1-based로 변환
                size = companyPage.size,
                total = companyPage.totalElements,
                totalPages = companyPage.totalPages,
                hasNext = companyPage.hasNext,
                hasPrevious = companyPage.hasPrevious
            )
        }
    }
}