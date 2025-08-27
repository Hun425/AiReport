package com.algoroadmap.domain.service

/**
 * JWT 토큰 서비스 도메인 인터페이스
 */
interface TokenService {
    fun generateToken(userId: Long, handle: String): String
    fun getUserIdFromToken(token: String): Long?
    fun getHandleFromToken(token: String): String?
    fun validateToken(token: String): Boolean
}