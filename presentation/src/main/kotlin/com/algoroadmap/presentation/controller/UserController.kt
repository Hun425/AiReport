package com.algoroadmap.presentation.controller

import com.algoroadmap.application.dto.UpdateSolvedAcHandleRequest
import com.algoroadmap.application.dto.UserResponse
import com.algoroadmap.application.service.AuthService
import com.algoroadmap.application.service.UserSyncApplicationService
import com.algoroadmap.presentation.security.SecurityUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "사용자 관련 API")
class UserController(
    private val authService: AuthService,
    private val userSyncApplicationService: UserSyncApplicationService
) {
    
    @Operation(
        summary = "내 정보 조회",
        description = "현재 로그인된 사용자의 기본 정보를 조회합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    )
    @GetMapping("/me")
    fun getMyInfo(): ResponseEntity<UserResponse> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(401).build()
        
        val user = authService.getUserById(userId)
            ?: return ResponseEntity.status(404).build()
        
        return ResponseEntity.ok(user)
    }
    
    @Operation(
        summary = "solved.ac 핸들 등록/수정",
        description = "사용자의 solved.ac 핸들을 등록하거나 수정합니다. 핸들 유효성을 solved.ac API로 검증합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "핸들 등록/수정 성공"),
        ApiResponse(responseCode = "400", description = "유효하지 않은 핸들"),
        ApiResponse(responseCode = "401", description = "인증 실패")
    )
    @PutMapping("/me/handle")
    fun updateSolvedAcHandle(
        @Valid @RequestBody request: UpdateSolvedAcHandleRequest
    ): ResponseEntity<Map<String, Any>> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(401).build()
        
        return runBlocking {
            try {
                authService.updateSolvedAcHandle(userId, request.handle)
                
                val response = mapOf(
                    "message" to "solved.ac 핸들이 성공적으로 등록되었습니다",
                    "handle" to request.handle
                )
                ResponseEntity.ok(response)
                
            } catch (e: Exception) {
                val errorResponse = mapOf(
                    "error" to mapOf(
                        "code" to "INVALID_HANDLE",
                        "message" to "유효하지 않은 solved.ac 핸들입니다",
                        "details" to e.message
                    )
                )
                ResponseEntity.badRequest().body(errorResponse)
            }
        }
    }
    
    @Operation(
        summary = "solved.ac 데이터 동기화 요청",
        description = "사용자의 solved.ac 문제 풀이 데이터를 서버에 비동기적으로 동기화하도록 요청합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "202", description = "동기화 시작됨"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "409", description = "이미 동기화 진행 중")
    )
    @PostMapping("/me/sync")
    fun requestUserSync(): ResponseEntity<Map<String, Any>> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(401).body(
                mapOf(
                    "error" to mapOf(
                        "code" to "UNAUTHORIZED",
                        "message" to "인증이 필요합니다."
                    )
                )
            )
        
        val response = runBlocking {
            userSyncApplicationService.requestUserSync(userId)
        }
        
        return if (response.isSuccess) {
            ResponseEntity.accepted().body(
                mapOf(
                    "message" to response.message,
                    "estimatedDuration" to response.estimatedDuration
                )
            )
        } else {
            throw RuntimeException("동기화 시작에 실패했습니다: ${response.message}")
        }
    }
    
    @Operation(
        summary = "동기화 상태 조회",
        description = "현재 동기화 진행 상황을 조회합니다.",
        security = [SecurityRequirement(name = "cookieAuth")]
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "상태 조회 성공"),
        ApiResponse(responseCode = "401", description = "인증 실패")
    )
    @GetMapping("/me/sync/status")
    fun getSyncStatus(): ResponseEntity<Any> {
        val userId = SecurityUtils.getCurrentUserId()
            ?: return ResponseEntity.status(401).body(
                mapOf(
                    "error" to mapOf(
                        "code" to "UNAUTHORIZED", 
                        "message" to "인증이 필요합니다."
                    )
                )
            )
        
        val status = userSyncApplicationService.getSyncStatus(userId)
        return ResponseEntity.ok(status)
    }
}