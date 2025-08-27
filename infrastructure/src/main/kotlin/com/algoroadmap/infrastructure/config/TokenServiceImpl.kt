package com.algoroadmap.infrastructure.config

import com.algoroadmap.domain.service.TokenService
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl(
    private val jwtTokenProvider: JwtTokenProvider
) : TokenService {
    
    override fun generateToken(userId: Long, handle: String): String {
        return jwtTokenProvider.generateToken(userId, handle)
    }
    
    override fun getUserIdFromToken(token: String): Long? {
        return jwtTokenProvider.getUserIdFromToken(token)
    }
    
    override fun getHandleFromToken(token: String): String? {
        return jwtTokenProvider.getHandleFromToken(token)
    }
    
    override fun validateToken(token: String): Boolean {
        return jwtTokenProvider.validateToken(token)
    }
}