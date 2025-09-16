package com.algoroadmap.domain.vo

/**
 * 문제 난이도 값 객체
 * "Bronze 1", "Silver 3", "Gold 2" 등의 난이도를 안전하게 관리
 */
@JvmInline
value class ProblemDifficulty(val value: String) {
    init {
        require(value.isNotBlank()) { "문제 난이도는 공백일 수 없습니다" }
        require(isValidDifficulty(value)) { "유효하지 않은 난이도 형식입니다: $value" }
    }

    /**
     * 난이도 레벨 반환 (1~6)
     * Bronze: 1, Silver: 2, Gold: 3, Platinum: 4, Diamond: 5, Ruby: 6
     */
    fun getLevel(): Int = when {
        value.startsWith("Bronze") -> 1
        value.startsWith("Silver") -> 2
        value.startsWith("Gold") -> 3
        value.startsWith("Platinum") -> 4
        value.startsWith("Diamond") -> 5
        value.startsWith("Ruby") -> 6
        else -> 0
    }

    /**
     * 난이도 세부 단계 반환 (1~5)
     */
    fun getSubLevel(): Int = when {
        value.contains(" 1") -> 1
        value.contains(" 2") -> 2
        value.contains(" 3") -> 3
        value.contains(" 4") -> 4
        value.contains(" 5") -> 5
        else -> 0
    }

    fun isEasy(): Boolean = getLevel() <= 2  // Bronze, Silver
    fun isHard(): Boolean = getLevel() >= 4  // Platinum 이상

    override fun toString(): String = value

    companion object {
        fun create(value: String?): ProblemDifficulty? {
            return if (value.isNullOrBlank()) null else ProblemDifficulty(value)
        }

        private fun isValidDifficulty(value: String): Boolean {
            val validTiers = listOf("Bronze", "Silver", "Gold", "Platinum", "Diamond", "Ruby")
            val validLevels = listOf("1", "2", "3", "4", "5")

            return validTiers.any { tier ->
                validLevels.any { level ->
                    value == "$tier $level"
                }
            }
        }
    }
}