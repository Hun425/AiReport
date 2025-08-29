package com.algoroadmap.domain.service

import com.algoroadmap.domain.entity.*

/**
 * 로드맵 생성 알고리즘을 담당하는 도메인 서비스 인터페이스
 */
interface RoadmapGenerationService {
    
    /**
     * 기업별 맞춤형 로드맵 생성
     * 
     * @param user 사용자 정보
     * @param company 목표 기업
     * @param durationInMonths 학습 기간 (개월)
     * @return 생성된 로드맵 데이터
     */
    fun generateRoadmap(user: User, company: Company, durationInMonths: Int): RoadmapGenerationResult
}

/**
 * 로드맵 생성 결과 데이터 클래스
 */
data class RoadmapGenerationResult(
    val user: User,
    val company: Company,
    val durationInMonths: Int,
    val weekPlans: List<WeekPlan>
)

/**
 * 주차별 계획 데이터 클래스
 */
data class WeekPlan(
    val weekNumber: Int,
    val title: String,
    val description: String,
    val problems: List<Problem>
)