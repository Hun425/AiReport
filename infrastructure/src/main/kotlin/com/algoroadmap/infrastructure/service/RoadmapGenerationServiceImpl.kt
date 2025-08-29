package com.algoroadmap.infrastructure.service

import com.algoroadmap.domain.entity.*
import com.algoroadmap.domain.repository.ProblemRepository
import com.algoroadmap.domain.service.RoadmapGenerationService
import com.algoroadmap.domain.service.RoadmapGenerationResult
import com.algoroadmap.domain.service.WeekPlan
import org.springframework.stereotype.Service

/**
 * 로드맵 생성 알고리즘 구현체
 */
@Service
class RoadmapGenerationServiceImpl(
    private val problemRepository: ProblemRepository
) : RoadmapGenerationService {
    
    override fun generateRoadmap(user: User, company: Company, durationInMonths: Int): RoadmapGenerationResult {
        val totalWeeks = durationInMonths * 4 // 1개월 = 4주 가정
        
        // 1. 기업의 중요 태그들 가져오기 (가중치 순으로 정렬)
        val companyTags = company.profileTags.sortedByDescending { it.weight }
        
        // 2. 사용자의 약점 태그 분석
        val userWeakTags = analyzeUserWeaknesses(user, companyTags.map { it.tagName })
        
        // 3. 주차별 커리큘럼 생성
        val weekPlans = generateWeeklyPlan(totalWeeks, companyTags, userWeakTags)
        
        return RoadmapGenerationResult(
            user = user,
            company = company,
            durationInMonths = durationInMonths,
            weekPlans = weekPlans
        )
    }
    
    private fun analyzeUserWeaknesses(user: User, importantTags: List<String>): List<String> {
        // 사용자가 푼 문제들의 태그 분포 분석
        val userTagCounts = mutableMapOf<String, Int>()
        
        user.solvedProblems.forEach { userSolved ->
            // 문제의 태그들을 가져와서 카운트 (실제로는 ProblemTag 엔티티가 필요)
            // 현재는 간단히 구현
            importantTags.forEach { tag ->
                if (isProblemRelatedToTag(userSolved.problem, tag)) {
                    userTagCounts[tag] = userTagCounts.getOrDefault(tag, 0) + 1
                }
            }
        }
        
        // 전체 문제 수 대비 각 태그별 해결 비율 계산
        val totalSolved = user.solvedCount
        val weakTags = importantTags.filter { tag ->
            val solvedCount = userTagCounts[tag] ?: 0
            val ratio = if (totalSolved > 0) solvedCount.toDouble() / totalSolved else 0.0
            ratio < 0.3 // 30% 미만이면 약점으로 판단
        }
        
        return weakTags
    }
    
    private fun generateWeeklyPlan(
        totalWeeks: Int,
        companyTags: List<CompanyProfileTag>,
        weakTags: List<String>
    ): List<WeekPlan> {
        val weeks = mutableListOf<WeekPlan>()
        
        // 약점 태그를 초반에 집중 학습하도록 배치
        val priorityTags = weakTags.take(3) // 상위 3개 약점 태그
        val remainingTags = companyTags.map { it.tagName }.minus(priorityTags.toSet())
        
        for (weekNumber in 1..totalWeeks) {
            val currentTag = when {
                weekNumber <= priorityTags.size -> priorityTags[weekNumber - 1]
                weekNumber <= totalWeeks - 2 -> {
                    // 중간 주차는 다양한 태그 순환
                    remainingTags[(weekNumber - priorityTags.size - 1) % remainingTags.size]
                }
                else -> {
                    // 마지막 2주는 종합 문제 또는 어려운 문제
                    companyTags.firstOrNull()?.tagName ?: "종합"
                }
            }
            
            val weekTitle = generateWeekTitle(currentTag, weekNumber, totalWeeks)
            val weekProblems = selectProblemsForWeek(currentTag, weekNumber, totalWeeks)
            
            val week = WeekPlan(
                weekNumber = weekNumber,
                title = weekTitle,
                description = generateWeekDescription(currentTag, weekNumber),
                problems = weekProblems
            )
            
            weeks.add(week)
        }
        
        return weeks
    }
    
    private fun generateWeekTitle(tag: String, weekNumber: Int, totalWeeks: Int): String {
        return when {
            weekNumber <= 3 -> "$tag 기초 다지기"
            weekNumber <= totalWeeks - 3 -> "$tag 심화 학습"
            else -> "$tag 실전 문제"
        }
    }
    
    private fun generateWeekDescription(tag: String, weekNumber: Int): String {
        return when {
            weekNumber <= 3 -> "$tag 의 기본 개념과 기초 문제들을 통해 개념을 확실히 잡아보세요."
            weekNumber <= 8 -> "$tag 유형의 다양한 패턴과 응용 문제들을 풀어보세요."
            else -> "실전 수준의 $tag 문제들로 실력을 완성해보세요."
        }
    }
    
    private fun selectProblemsForWeek(tag: String, weekNumber: Int, totalWeeks: Int): List<Problem> {
        // 난이도 결정 (주차가 진행될수록 어려워짐)
        val difficultyLevel = when {
            weekNumber <= totalWeeks / 3 -> "basic"    // 기초
            weekNumber <= totalWeeks * 2 / 3 -> "intermediate"  // 중급
            else -> "advanced" // 고급
        }
        
        // 해당 태그와 난이도에 맞는 문제들 조회
        return problemRepository.findByTagAndDifficulty(tag, difficultyLevel, 5)
    }
    
    private fun isProblemRelatedToTag(problem: Problem, tag: String): Boolean {
        // 실제로는 ProblemTag 엔티티를 통해 확인해야 함
        // 현재는 간단히 문제 제목으로 추정
        return when (tag.lowercase()) {
            "dp" -> problem.title.contains("피보나치") || problem.title.contains("타일링") || problem.title.contains("계단")
            "그래프 탐색" -> problem.title.contains("경로") || problem.title.contains("탐색")
            "구현" -> problem.title.contains("시뮬레이션") || problem.title.contains("구현")
            else -> false
        }
    }
}