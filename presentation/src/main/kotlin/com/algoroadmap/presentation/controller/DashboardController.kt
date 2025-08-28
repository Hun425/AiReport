package com.algoroadmap.presentation.controller

import com.algoroadmap.application.dto.DashboardResponse
import com.algoroadmap.application.service.DashboardApplicationService
import com.algoroadmap.presentation.security.SecurityUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "대시보드 관련 API")
class DashboardController(
    private val dashboardApplicationService: DashboardApplicationService
) {
    
    @Operation(
        summary = "대시보드 데이터 조회",
        description = "메인 대시보드에 필요한 모든 데이터를 조회합니다. (내 정보 + 유형별 분석 데이터 + 최근 리뷰)",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "대시보드 데이터 조회 성공"),
        ApiResponse(responseCode = "400", description = "동기화되지 않은 사용자"),
        ApiResponse(responseCode = "401", description = "인증 실패")
    )
    @GetMapping("/me")
    fun getDashboardData(): ResponseEntity<DashboardResponse> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(401).build()
        
        val dashboardData = dashboardApplicationService.getDashboardData(userId)
        return ResponseEntity.ok(dashboardData)
    }
}