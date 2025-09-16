package com.algoroadmap.domain.vo

/**
 * 이메일 값 객체
 * 사용자의 이메일 주소를 안전하게 관리
 */
@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotBlank()) { "이메일은 공백일 수 없습니다" }
        require(isValidEmail(value)) { "유효하지 않은 이메일 형식입니다: $value" }
    }

    fun getDomain(): String = value.substringAfter("@")
    fun getLocalPart(): String = value.substringBefore("@")

    override fun toString(): String = value

    companion object {
        fun create(value: String?): Email? {
            return if (value.isNullOrBlank()) null else Email(value)
        }

        private fun isValidEmail(value: String): Boolean {
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            return value.matches(emailRegex)
        }
    }
}