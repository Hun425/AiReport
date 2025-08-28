package com.algoroadmap.infrastructure.external;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B-\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u0096@\u00a2\u0006\u0002\u0010\fJ\u0012\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005H\u0016J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\u0096@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/algoroadmap/infrastructure/external/SolvedAcOAuthServiceImpl;", "Lcom/algoroadmap/domain/service/OAuthService;", "webClient", "Lorg/springframework/web/reactive/function/client/WebClient;", "clientId", "", "clientSecret", "redirectUri", "(Lorg/springframework/web/reactive/function/client/WebClient;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "exchangeCodeForToken", "Lcom/algoroadmap/domain/service/OAuthService$TokenResponse;", "code", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAuthorizationUrl", "state", "getUserInfo", "Lcom/algoroadmap/domain/service/OAuthService$UserInfo;", "accessToken", "infrastructure"})
public class SolvedAcOAuthServiceImpl implements com.algoroadmap.domain.service.OAuthService {
    @org.jetbrains.annotations.NotNull()
    private final org.springframework.web.reactive.function.client.WebClient webClient = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String clientId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String clientSecret = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String redirectUri = null;
    
    public SolvedAcOAuthServiceImpl(@org.springframework.beans.factory.annotation.Qualifier(value = "oauthWebClient")
    @org.jetbrains.annotations.NotNull()
    org.springframework.web.reactive.function.client.WebClient webClient, @org.springframework.beans.factory.annotation.Value(value = "${spring.security.oauth2.client.registration.solved-ac.client-id}")
    @org.jetbrains.annotations.NotNull()
    java.lang.String clientId, @org.springframework.beans.factory.annotation.Value(value = "${spring.security.oauth2.client.registration.solved-ac.client-secret}")
    @org.jetbrains.annotations.NotNull()
    java.lang.String clientSecret, @org.springframework.beans.factory.annotation.Value(value = "${spring.security.oauth2.client.registration.solved-ac.redirect-uri}")
    @org.jetbrains.annotations.NotNull()
    java.lang.String redirectUri) {
        super();
    }
    
    /**
     * OAuth 인증 URL 생성
     */
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getAuthorizationUrl(@org.jetbrains.annotations.Nullable()
    java.lang.String state) {
        return null;
    }
    
    /**
     * 인증 코드로 액세스 토큰 획득
     */
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object exchangeCodeForToken(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.algoroadmap.domain.service.OAuthService.TokenResponse> $completion) {
        return null;
    }
    
    /**
     * 액세스 토큰으로 사용자 정보 조회
     */
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getUserInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String accessToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.algoroadmap.domain.service.OAuthService.UserInfo> $completion) {
        return null;
    }
}