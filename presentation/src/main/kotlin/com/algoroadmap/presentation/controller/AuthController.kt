package com.algoroadmap.presentation.controller

import com.algoroadmap.application.dto.OAuthCallbackRequest
import com.algoroadmap.application.service.AuthService
import com.algoroadmap.domain.exception.DomainException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    
    /**
     * solved.ac OAuth 시작 - 사용자를 solved.ac 인증 페이지로 리디렉션
     */
    @GetMapping("/solvedac")
    fun startSolvedAcAuth(response: HttpServletResponse) {
        // TODO: 실제 solved.ac OAuth URL로 리디렉션
        // 현재는 Mock 테스트용으로 바로 콜백으로 리디렉션
        response.sendRedirect("/api/v1/auth/solvedac/callback?code=mock-auth-code")
    }
    
    /**
     * solved.ac OAuth 콜백 처리
     */
    @GetMapping("/solvedac/callback")
    fun handleSolvedAcCallback(
        @RequestParam code: String,
        @RequestParam(required = false) state: String?,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, Any>> {
        
        return try {
            val callbackRequest = OAuthCallbackRequest(code, state)
            
            // 비동기 처리를 동기적으로 실행 (Controller에서는 runBlocking 사용)
            val authResult = runBlocking { 
                authService.handleSolvedAcCallback(callbackRequest) 
            }
            
            // JWT 토큰을 HttpOnly 쿠키로 설정
            val jwtCookie = Cookie("accessToken", authResult.accessToken).apply {
                isHttpOnly = true
                secure = false // 개발환경에서는 false, 운영환경에서는 true
                path = "/"
                maxAge = 3600 // 1시간
            }
            response.addCookie(jwtCookie)
            
            // 온보딩 페이지로 리디렉션
            response.sendRedirect("/onboarding")
            
            val successResponse = mapOf(
                "message" to "OAuth 인증이 완료되었습니다",
                "user" to authResult.user,
                "isNewUser" to authResult.isNewUser
            )
            
            ResponseEntity.ok(successResponse)
            
        } catch (e: DomainException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to e::class.simpleName,
                    "message" to e.message,
                    "details" to "OAuth 인증 처리 중 오류가 발생했습니다"
                )
            )
            ResponseEntity.badRequest().body(errorResponse)
            
        } catch (e: Exception) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to "OAUTH_ERROR",
                    "message" to "OAuth 인증 중 오류가 발생했습니다",
                    "details" to (e.message ?: "Unknown error")
                )
            )
            ResponseEntity.internalServerError().body(errorResponse)
        }
    }
    
    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        // JWT 토큰 쿠키 제거
        val expiredCookie = Cookie("accessToken", "").apply {
            isHttpOnly = true
            path = "/"
            maxAge = 0 // 즉시 만료
        }
        response.addCookie(expiredCookie)
        
        val logoutResponse = mapOf(
            "message" to "로그아웃되었습니다"
        )
        return ResponseEntity.ok(logoutResponse)
    }
}