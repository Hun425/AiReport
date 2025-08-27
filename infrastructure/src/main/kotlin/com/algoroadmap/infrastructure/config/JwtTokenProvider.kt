package com.algoroadmap.infrastructure.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
    
    /**
     * JWT 토큰 생성
     */
    fun generateToken(userId: Long, handle: String): String {
        val now = Date()
        val expiryDate = Date(now.time + jwtProperties.expiration)
        
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("handle", handle)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
    
    /**
     * JWT 토큰에서 사용자 ID 추출
     */
    fun getUserIdFromToken(token: String): Long? {
        return try {
            val claims = getClaimsFromToken(token)
            claims.subject.toLong()
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * JWT 토큰에서 사용자 핸들 추출
     */
    fun getHandleFromToken(token: String): String? {
        return try {
            val claims = getClaimsFromToken(token)
            claims.get("handle", String::class.java)
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * JWT 토큰 유효성 검증
     */
    fun validateToken(token: String): Boolean {
        return try {
            getClaimsFromToken(token)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}