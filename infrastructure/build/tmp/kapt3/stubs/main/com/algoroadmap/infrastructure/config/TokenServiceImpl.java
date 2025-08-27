package com.algoroadmap.infrastructure.config;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0017\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/algoroadmap/infrastructure/config/TokenServiceImpl;", "Lcom/algoroadmap/domain/service/TokenService;", "jwtTokenProvider", "Lcom/algoroadmap/infrastructure/config/JwtTokenProvider;", "(Lcom/algoroadmap/infrastructure/config/JwtTokenProvider;)V", "generateToken", "", "userId", "", "handle", "getHandleFromToken", "token", "getUserIdFromToken", "(Ljava/lang/String;)Ljava/lang/Long;", "validateToken", "", "infrastructure"})
public class TokenServiceImpl implements com.algoroadmap.domain.service.TokenService {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.config.JwtTokenProvider jwtTokenProvider = null;
    
    public TokenServiceImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.config.JwtTokenProvider jwtTokenProvider) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String generateToken(long userId, @org.jetbrains.annotations.NotNull()
    java.lang.String handle) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Long getUserIdFromToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.String getHandleFromToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
        return null;
    }
    
    @java.lang.Override()
    public boolean validateToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
        return false;
    }
}