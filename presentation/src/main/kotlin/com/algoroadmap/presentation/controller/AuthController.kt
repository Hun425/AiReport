package com.algoroadmap.presentation.controller

import com.algoroadmap.application.dto.OAuthCallbackRequest
import com.algoroadmap.application.service.AuthService
import com.algoroadmap.domain.exception.DomainException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "사용자 인증 관련 API")
class AuthController(
    private val authService: AuthService
) {
    
    @Operation(
        summary = "Google OAuth 인증 시작",
        description = "사용자를 Google의 OAuth 인증 페이지로 리디렉션시킵니다."
    )
    @ApiResponses(
        ApiResponse(responseCode = "302", description = "Google 인증 페이지로 리디렉션"),
        ApiResponse(responseCode = "500", description = "OAuth 설정 오류")
    )
    @GetMapping("/google")
    fun startGoogleAuth(
        response: HttpServletResponse,
        @Parameter(description = "OAuth state 파라미터") 
        @RequestParam(required = false) state: String?
    ) {
        val authorizationUrl = authService.getGoogleAuthorizationUrl(state)
        response.sendRedirect(authorizationUrl)
    }
    
    /**
     * Google OAuth 콜백 처리
     */
    @GetMapping("/google/callback")
    fun handleGoogleCallback(
        @RequestParam code: String,
        @RequestParam(required = false) state: String?,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, Any>> {
        
        return try {
            val callbackRequest = OAuthCallbackRequest(code, state)
            
            // 비동기 처리를 동기적으로 실행 (Controller에서는 runBlocking 사용)
            val authResult = runBlocking { 
                authService.handleGoogleCallback(callbackRequest) 
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
            
        } catch (e: DomainException.OAuthAuthenticationException) {
            val errorResponse = mapOf(
                "error" to mapOf(
                    "code" to "OAUTH_AUTHENTICATION_FAILED",
                    "message" to "OAuth 인증에 실패했습니다",
                    "details" to e.message
                )
            )
            ResponseEntity.badRequest().body(errorResponse)
            
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