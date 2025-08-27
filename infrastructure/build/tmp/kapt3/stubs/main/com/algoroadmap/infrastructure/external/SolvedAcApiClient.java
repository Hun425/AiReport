package com.algoroadmap.infrastructure.external;

@org.springframework.stereotype.Component()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/algoroadmap/infrastructure/external/SolvedAcApiClient;", "", "solvedAcWebClient", "Lorg/springframework/web/reactive/function/client/WebClient;", "(Lorg/springframework/web/reactive/function/client/WebClient;)V", "fetchUserData", "Lcom/algoroadmap/infrastructure/external/SolvedAcUser;", "handle", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchUserSolvedProblems", "", "Lcom/algoroadmap/infrastructure/external/SolvedAcProblem;", "infrastructure"})
public class SolvedAcApiClient {
    @org.jetbrains.annotations.NotNull()
    private final org.springframework.web.reactive.function.client.WebClient solvedAcWebClient = null;
    
    public SolvedAcApiClient(@org.jetbrains.annotations.NotNull()
    org.springframework.web.reactive.function.client.WebClient solvedAcWebClient) {
        super();
    }
    
    /**
     * solved.ac API로부터 사용자 정보를 조회
     */
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object fetchUserData(@org.jetbrains.annotations.NotNull()
    java.lang.String handle, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.algoroadmap.infrastructure.external.SolvedAcUser> $completion) {
        return null;
    }
    
    /**
     * 사용자가 푼 문제 목록 조회
     */
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object fetchUserSolvedProblems(@org.jetbrains.annotations.NotNull()
    java.lang.String handle, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.algoroadmap.infrastructure.external.SolvedAcProblem>> $completion) {
        return null;
    }
}