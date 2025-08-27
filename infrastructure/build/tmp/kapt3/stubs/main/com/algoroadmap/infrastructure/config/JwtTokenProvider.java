package com.algoroadmap.infrastructure.config;

@org.springframework.stereotype.Component()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0012J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0017\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\bH\u0016\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/algoroadmap/infrastructure/config/JwtTokenProvider;", "", "jwtProperties", "Lcom/algoroadmap/infrastructure/config/JwtProperties;", "(Lcom/algoroadmap/infrastructure/config/JwtProperties;)V", "key", "Ljava/security/Key;", "generateToken", "", "userId", "", "handle", "getClaimsFromToken", "Lio/jsonwebtoken/Claims;", "token", "getHandleFromToken", "getUserIdFromToken", "(Ljava/lang/String;)Ljava/lang/Long;", "validateToken", "", "infrastructure"})
public class JwtTokenProvider {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.config.JwtProperties jwtProperties = null;
    @org.jetbrains.annotations.NotNull()
    private final java.security.Key key = null;
    
    public JwtTokenProvider(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.config.JwtProperties jwtProperties) {
        super();
    }
    
    /**
     * JWT 토큰 생성
     */
    @org.jetbrains.annotations.NotNull()
    public java.lang.String generateToken(long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String handle) {
        return null;
    }
    
    /**
     * JWT 토큰에서 사용자 ID 추출
     */
    @org.jetbrains.annotations.Nullable()
    public java.lang.Long getUserIdFromToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
        return null;
    }
    
    /**
     * JWT 토큰에서 사용자 핸들 추출
     */
    @org.jetbrains.annotations.Nullable()
    public java.lang.String getHandleFromToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
        return null;
    }
    
    /**
     * JWT 토큰 유효성 검증
     */
    public boolean validateToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
        return false;
    }
    
    private io.jsonwebtoken.Claims getClaimsFromToken(java.lang.String token) {
        return null;
    }
}