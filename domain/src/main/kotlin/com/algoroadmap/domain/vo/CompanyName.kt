package com.algoroadmap.domain.vo

/**
 * 회사명 값 객체
 * 회사 이름을 안전하게 관리
 */
@JvmInline
value class CompanyName(val value: String) {
    init {
        require(value.isNotBlank()) { "회사명은 공백일 수 없습니다" }
        require(value.length in 1..100) { "회사명은 1~100자 사이여야 합니다" }
    }

    fun isKoreanCompany(): Boolean {
        val koreanCompanies = listOf("네이버", "카카오", "라인", "쿠팡", "토스", "우아한형제들", "NHN")
        return koreanCompanies.any { value.contains(it) }
    }

    override fun toString(): String = value

    companion object {
        fun create(value: String?): CompanyName? {
            return if (value.isNullOrBlank()) null else CompanyName(value)
        }
    }
}