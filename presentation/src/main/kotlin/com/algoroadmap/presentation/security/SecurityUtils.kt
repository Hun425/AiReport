package com.algoroadmap.presentation.security

import com.algoroadmap.infrastructure.security.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

/**
 * 보안 관련 유틸리티 함수들
 */
object SecurityUtils {
    
    /**
     * 현재 인증된 사용자의 ID를 반환
     */
    fun getCurrentUserId(): Long? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is UserPrincipal) {
            (authentication.principal as UserPrincipal).userId
        } else {
            null
        }
    }
    
    /**
     * 현재 인증된 사용자의 handle을 반환
     */
    fun getCurrentUserHandle(): String? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is UserPrincipal) {
            (authentication.principal as UserPrincipal).handle
        } else {
            null
        }
    }
    
    /**
     * 현재 인증된 사용자 정보를 반환
     */
    fun getCurrentUser(): UserPrincipal? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is UserPrincipal) {
            authentication.principal as UserPrincipal
        } else {
            null
        }
    }
    
    /**
     * 사용자가 인증되었는지 확인
     */
    fun isAuthenticated(): Boolean {
        return getCurrentUserId() != null
    }
}