package com.algoroadmap.domain.vo

/**
 * solved.ac 핸들 값 객체
 * 사용자의 solved.ac 계정 아이디를 안전하게 관리
 */
@JvmInline
value class SolvedAcHandle(val value: String) {
    init {
        require(value.isNotBlank()) { "solved.ac 핸들은 공백일 수 없습니다" }
        require(value.length in 3..50) { "solved.ac 핸들은 3~50자 사이여야 합니다" }
        require(value.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            "solved.ac 핸들은 영문, 숫자, 언더스코어만 사용 가능합니다"
        }
    }

    fun isEmpty(): Boolean = value.isBlank()
    fun isNotEmpty(): Boolean = value.isNotBlank()

    override fun toString(): String = value

    companion object {
        fun create(value: String?): SolvedAcHandle? {
            return if (value.isNullOrBlank()) null else SolvedAcHandle(value)
        }
    }
}