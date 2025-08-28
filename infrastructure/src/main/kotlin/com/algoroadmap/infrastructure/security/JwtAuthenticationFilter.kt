package com.algoroadmap.infrastructure.security

import com.algoroadmap.domain.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractTokenFromRequest(request)
        
        if (token != null && tokenService.validateToken(token)) {
            val userId = tokenService.getUserIdFromToken(token)
            val handle = tokenService.getHandleFromToken(token)
            
            if (userId != null && handle != null) {
                val userPrincipal = UserPrincipal(userId, handle)
                val authentication = UsernamePasswordAuthenticationToken(
                    userPrincipal, null, emptyList()
                )
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        
        filterChain.doFilter(request, response)
    }
    
    private fun extractTokenFromRequest(request: HttpServletRequest): String? {
        // 1. Header에서 Bearer 토큰 추출
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        
        // 2. Cookie에서 토큰 추출 (OAuth 콜백 후)
        val cookies = request.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == "accessToken") {
                    return cookie.value
                }
            }
        }
        
        return null
    }
}

/**
 * 인증된 사용자 정보
 */
data class UserPrincipal(
    val userId: Long,
    val handle: String
)