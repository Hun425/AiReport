package com.algoroadmap.presentation.controller

import com.algoroadmap.application.service.UserSyncApplicationService
import com.algoroadmap.domain.exception.DomainException
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
        // TODO: JWT에서 사용자 ID 추출 (현재는 하드코딩)
        val userId = 1L
        
        return try {
            val response = runBlocking {
                userSyncApplicationService.requestUserSync(userId)
            }
            
            if (response.isSuccess) {
                ResponseEntity.accepted().body(
                    mapOf(
                        "message" to response.message,
                        "estimatedDuration" to response.estimatedDuration
                    )
                )
            } else {
                ResponseEntity.badRequest().body(
                    mapOf(
                        "error" to mapOf(
                            "code" to "SYNC_START_FAILED",
                            "message" to response.message
                        )
                    )
                )
            }
            
        } catch (e: DomainException.UserNotFoundException) {
            ResponseEntity.notFound().build()
            
        } catch (e: DomainException.SyncInProgressException) {
            ResponseEntity.status(409).body(
                mapOf(
                    "error" to mapOf(
                        "code" to "SYNC_IN_PROGRESS",
                        "message" to "이미 동기화가 진행 중입니다.",
                        "details" to e.message
                    )
                )
            )
            
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(
                mapOf(
                    "error" to mapOf(
                        "code" to "INTERNAL_SERVER_ERROR",
                        "message" to "서버 내부 오류가 발생했습니다.",
                        "details" to e.message
                    )
                )
            )
        }
    }
    
    /**
     * 동기화 상태 조회
     */
    @GetMapping("/me/sync/status")
    fun getSyncStatus(): ResponseEntity<Any> {
        // TODO: JWT에서 사용자 ID 추출 (현재는 하드코딩)
        val userId = 1L
        
        return try {
            val status = userSyncApplicationService.getSyncStatus(userId)
            ResponseEntity.ok(status)
            
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(
                mapOf(
                    "error" to mapOf(
                        "code" to "INTERNAL_SERVER_ERROR",
                        "message" to "동기화 상태 조회에 실패했습니다.",
                        "details" to e.message
                    )
                )
            )
        }
    }
}