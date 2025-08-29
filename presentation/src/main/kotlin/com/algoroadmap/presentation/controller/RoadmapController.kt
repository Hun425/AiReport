package com.algoroadmap.presentation.controller

import com.algoroadmap.application.dto.CreateRoadmapRequest
import com.algoroadmap.application.dto.CreateRoadmapResponse
import com.algoroadmap.application.dto.RoadmapDetailResponse
import com.algoroadmap.application.service.RoadmapApplicationService
import com.algoroadmap.presentation.security.SecurityUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/roadmaps")
@Tag(name = "Roadmap", description = "로드맵 관련 API")
class RoadmapController(
    private val roadmapApplicationService: RoadmapApplicationService
) {
    
    @Operation(
        summary = "로드맵 생성",
        description = "목표 기업과 기간을 설정하여 새로운 개인화된 로드맵을 생성합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "로드맵 생성 성공"),
        ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "409", description = "이미 활성화된 로드맵 존재")
    )
    @PostMapping
    fun createRoadmap(
        @RequestBody request: CreateRoadmapRequest
    ): ResponseEntity<CreateRoadmapResponse> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val response = roadmapApplicationService.createRoadmap(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
    
    @Operation(
        summary = "내 로드맵 조회",
        description = "현재 사용자가 진행 중인 활성화된 로드맵의 상세 내용을 조회합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로드맵 조회 성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "404", description = "활성화된 로드맵이 없음")
    )
    @GetMapping("/me")
    fun getMyRoadmap(
        @Parameter(description = "특정 주차만 조회 (선택사항)", example = "1")
        @RequestParam(required = false) week: Int?
    ): ResponseEntity<RoadmapDetailResponse> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val roadmap = roadmapApplicationService.getMyActiveRoadmap(userId, week)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        
        return ResponseEntity.ok(roadmap)
    }
    
    @Operation(
        summary = "특정 로드맵 조회",
        description = "로드맵 ID로 특정 로드맵의 상세 내용을 조회합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "로드맵 조회 성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "403", description = "로드맵 접근 권한 없음"),
        ApiResponse(responseCode = "404", description = "로드맵을 찾을 수 없음")
    )
    @GetMapping("/{roadmapId}")
    fun getRoadmapById(
        @Parameter(description = "로드맵 ID", example = "1")
        @PathVariable roadmapId: Long
    ): ResponseEntity<RoadmapDetailResponse> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        val roadmap = roadmapApplicationService.getRoadmapById(userId, roadmapId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        
        return ResponseEntity.ok(roadmap)
    }
    
    @Operation(
        summary = "로드맵 비활성화",
        description = "현재 활성화된 로드맵을 비활성화합니다. (새로운 로드맵 생성을 위해 필요)",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "로드맵 비활성화 성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "403", description = "로드맵 접근 권한 없음"),
        ApiResponse(responseCode = "404", description = "로드맵을 찾을 수 없음")
    )
    @DeleteMapping("/{roadmapId}")
    fun deactivateRoadmap(
        @Parameter(description = "로드맵 ID", example = "1")
        @PathVariable roadmapId: Long
    ): ResponseEntity<Void> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        
        roadmapApplicationService.deactivateRoadmap(userId, roadmapId)
        return ResponseEntity.noContent().build()
    }
}