package com.algoroadmap.presentation.controller

import com.algoroadmap.application.service.UserSyncApplicationService
import com.algoroadmap.domain.exception.DomainException
import com.algoroadmap.presentation.security.SecurityUtils
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserSyncController(
    private val userSyncApplicationService: UserSyncApplicationService
) {
    
    /**
     * 사용자 데이터 동기화 요청
     */
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
    
    /**
     * 동기화 상태 조회
     */
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