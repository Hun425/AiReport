package com.algoroadmap.application.service

import com.algoroadmap.application.dto.*
import com.algoroadmap.domain.entity.*
import com.algoroadmap.domain.exception.DomainException
import com.algoroadmap.domain.repository.*
import com.algoroadmap.domain.service.RoadmapGenerationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 로드맵 관련 Application Service
 */
@Service
@Transactional(readOnly = true)
class RoadmapApplicationService(
    private val userRepository: UserRepository,
    private val companyRepository: CompanyRepository,
    private val roadmapRepository: RoadmapRepository,
    private val roadmapWeekRepository: RoadmapWeekRepository,
    private val roadmapProblemRepository: RoadmapProblemRepository,
    private val roadmapGenerationService: RoadmapGenerationService
) {
    
    /**
     * 새로운 로드맵 생성
     */
    @Transactional
    fun createRoadmap(userId: Long, request: CreateRoadmapRequest): CreateRoadmapResponse {
        // 1. 사용자 조회
        val user = userRepository.findById(userId)
            ?: throw DomainException.UserNotFoundException(userId)
        
        // 2. 기업 조회
        val company = companyRepository.findById(request.companyId)
            ?: throw DomainException.CompanyNotFoundException(request.companyId)
        
        // 3. 기존 활성화된 로드맵 확인
        if (roadmapRepository.existsActiveRoadmapByUserId(userId)) {
            throw DomainException.ActiveRoadmapAlreadyExistsException()
        }
        
        // 4. 입력값 검증
        validateRoadmapRequest(request)
        
        // 5. 로드맵 생성 알고리즘 실행
        val generationResult = roadmapGenerationService.generateRoadmap(user, company, request.durationInMonths)
        
        // 6. 로드맵 엔티티 생성 및 저장
        val roadmap = Roadmap(
            user = user,
            company = company,
            durationInMonths = request.durationInMonths
        )
        val savedRoadmap = roadmapRepository.save(roadmap)
        
        // 7. 주차별 데이터 생성 및 저장
        val weeks: List<RoadmapWeek> = generationResult.weekPlans.map { plan ->
            RoadmapWeek(
                roadmap = savedRoadmap,
                weekNumber = plan.weekNumber,
                title = plan.title,
                description = plan.description
            )
        }
        val savedWeeks: List<RoadmapWeek> = roadmapWeekRepository.saveAll(weeks)
        
        // 8. 주차별 문제 데이터 생성 및 저장
        val allProblems = mutableListOf<RoadmapProblem>()
        generationResult.weekPlans.forEachIndexed { weekIndex, plan ->
            val week: RoadmapWeek = savedWeeks[weekIndex]
            val problems: List<RoadmapProblem> = plan.problems.mapIndexed { problemIndex, problem ->
                RoadmapProblem(
                    week = week,
                    problem = problem,
                    orderInWeek = problemIndex + 1,
                    recommendedTag = "추천",
                    difficultyReason = "기업 출제 경향 기반 추천"
                )
            }
            allProblems.addAll(problems)
        }
        roadmapProblemRepository.saveAll(allProblems)
        
        return CreateRoadmapResponse(
            roadmapId = savedRoadmap.id,
            message = "로드맵이 성공적으로 생성되었습니다."
        )
    }
    
    /**
     * 내 활성화된 로드맵 조회
     */
    fun getMyActiveRoadmap(userId: Long, weekFilter: Int? = null): RoadmapDetailResponse? {
        // 1. 사용자 확인
        val user = userRepository.findById(userId)
            ?: throw DomainException.UserNotFoundException(userId)
        
        // 2. 활성화된 로드맵 조회
        val roadmap = roadmapRepository.findActiveByUserId(userId)
            ?: return null
        
        // 3. 특정 주차 필터링 처리
        if (weekFilter != null) {
            val targetWeek = roadmapWeekRepository.findByRoadmapIdAndWeekNumber(roadmap.id, weekFilter)
            // 특정 주차만 필터링하여 weeks 리스트 수정
            roadmap.weeks.clear()
            targetWeek?.let { roadmap.weeks.add(it) }
        }

        val filteredRoadmap = roadmap
        
        return filteredRoadmap.toDetailResponse()
    }
    
    /**
     * 특정 로드맵 조회
     */
    fun getRoadmapById(userId: Long, roadmapId: Long): RoadmapDetailResponse? {
        // 1. 로드맵 조회 및 권한 확인
        val roadmap = roadmapRepository.findById(roadmapId)
            ?: return null
        
        if (roadmap.user.id != userId) {
            throw DomainException.UnauthorizedRoadmapAccessException()
        }
        
        return roadmap.toDetailResponse()
    }
    
    /**
     * 로드맵 비활성화 (새로운 로드맵 생성을 위해)
     */
    @Transactional
    fun deactivateRoadmap(userId: Long, roadmapId: Long) {
        val roadmap = roadmapRepository.findById(roadmapId)
            ?: throw DomainException.RoadmapNotFoundException(roadmapId)
        
        if (roadmap.user.id != userId) {
            throw DomainException.UnauthorizedRoadmapAccessException()
        }
        
        // 해당 사용자의 모든 로드맵 비활성화
        roadmapRepository.deactivateAllByUserId(userId)
    }
    
    /**
     * 로드맵 생성 요청 검증
     */
    private fun validateRoadmapRequest(request: CreateRoadmapRequest) {
        if (request.companyId <= 0) {
            throw DomainException.InvalidCompanyIdException()
        }
        
        if (request.durationInMonths !in 1..12) {
            throw DomainException.InvalidDurationException()
        }
    }
}