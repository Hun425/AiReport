package com.algoroadmap.presentation.controller

import com.algoroadmap.application.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val authService: AuthService
) {
    
    /**
     * 내 정보 조회
     */
    @GetMapping("/me")
    fun getMyInfo(request: HttpServletRequest): ResponseEntity<Map<String, Any>> {
        return try {
            // 쿠키에서 JWT 토큰 추출
            val accessToken = request.cookies
                ?.find { it.name == "accessToken" }
                ?.value
            
            if (accessToken == null) {
                return ResponseEntity.status(401).body(
                    mapOf(
                        "error" to mapOf(
                            "code" to "UNAUTHORIZED",
                            "message" to "JWT 토큰이 없습니다"
                        )
                    )
                )
            }
            
            // 토큰 유효성 검증
            if (!authService.validateToken(accessToken)) {
                return ResponseEntity.status(401).body(
                    mapOf(
                        "error" to mapOf(
                            "code" to "UNAUTHORIZED",
                            "message" to "JWT 토큰이 유효하지 않습니다"
                        )
                    )
                )
            }
            
            // 사용자 정보 조회
            val user = authService.getUserFromToken(accessToken)
                ?: return ResponseEntity.status(404).body(
                    mapOf(
                        "error" to mapOf(
                            "code" to "USER_NOT_FOUND",
                            "message" to "사용자 정보를 찾을 수 없습니다"
                        )
                    )
                )
            
            ResponseEntity.ok(mapOf("user" to user))
            
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(
                mapOf(
                    "error" to mapOf(
                        "code" to "INTERNAL_ERROR",
                        "message" to "사용자 정보 조회 중 오류가 발생했습니다",
                        "details" to e.message
                    )
                )
            )
        }
    }
}