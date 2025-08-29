package com.algoroadmap.presentation.controller

import com.algoroadmap.application.dto.CompanyInfo
import com.algoroadmap.application.dto.CompanyListResponse
import com.algoroadmap.application.service.CompanyApplicationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/companies")
@Tag(name = "Company", description = "기업 관련 API")
class CompanyController(
    private val companyApplicationService: CompanyApplicationService
) {
    
    @Operation(
        summary = "기업 목록 조회",
        description = "로드맵 생성 시 선택할 수 있는 기업 목록을 조회합니다. 페이징을 지원합니다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "기업 목록 조회 성공"),
        ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
    )
    @GetMapping
    fun getCompanies(
        @Parameter(description = "페이지 번호 (1부터 시작)", example = "1")
        @RequestParam(defaultValue = "1") page: Int,
        
        @Parameter(description = "페이지 크기 (최대 100)", example = "20")
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<CompanyListResponse> {
        
        if (page <= 0) {
            return ResponseEntity.badRequest().build()
        }
        
        val response = companyApplicationService.getCompanies(page, size)
        return ResponseEntity.ok(response)
    }
    
    @Operation(
        summary = "특정 기업 조회",
        description = "ID로 특정 기업의 정보를 조회합니다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "기업 조회 성공"),
        ApiResponse(responseCode = "404", description = "기업을 찾을 수 없음")
    )
    @GetMapping("/{id}")
    fun getCompanyById(
        @Parameter(description = "기업 ID", example = "1")
        @PathVariable id: Long
    ): ResponseEntity<CompanyInfo> {
        
        val company = companyApplicationService.getCompanyById(id)
            ?: return ResponseEntity.notFound().build()
        
        return ResponseEntity.ok(company)
    }
}