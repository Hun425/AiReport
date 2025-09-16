package com.algoroadmap.domain.vo

/**
 * 문제 ID 값 객체
 * BOJ 문제 번호를 안전하게 관리
 */
@JvmInline
value class ProblemId(val value: Long) {
    init {
        require(value > 0) { "문제 ID는 양수여야 합니다" }
        require(value <= 999999) { "문제 ID는 6자리 이하여야 합니다" }
    }

    fun toBojUrl(): String = "https://www.acmicpc.net/problem/$value"

    override fun toString(): String = value.toString()

    companion object {
        fun create(value: Long?): ProblemId? {
            return if (value == null || value <= 0) null else ProblemId(value)
        }
    }
}