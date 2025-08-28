package com.algoroadmap.infrastructure.config;

@org.springframework.context.annotation.Configuration()
@org.springframework.security.config.annotation.web.configuration.EnableWebSecurity()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/algoroadmap/infrastructure/config/SecurityConfig;", "", "jwtAuthenticationFilter", "Lcom/algoroadmap/infrastructure/security/JwtAuthenticationFilter;", "(Lcom/algoroadmap/infrastructure/security/JwtAuthenticationFilter;)V", "filterChain", "Lorg/springframework/security/web/SecurityFilterChain;", "http", "Lorg/springframework/security/config/annotation/web/builders/HttpSecurity;", "infrastructure"})
public class SecurityConfig {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.security.JwtAuthenticationFilter jwtAuthenticationFilter = null;
    
    public SecurityConfig(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.security.JwtAuthenticationFilter jwtAuthenticationFilter) {
        super();
    }
    
    @org.springframework.context.annotation.Bean()
    @org.jetbrains.annotations.NotNull()
    public org.springframework.security.web.SecurityFilterChain filterChain(@org.jetbrains.annotations.NotNull()
    org.springframework.security.config.annotation.web.builders.HttpSecurity http) {
        return null;
    }
}