package com.algoroadmap.infrastructure.external;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0012\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/algoroadmap/infrastructure/external/SolvedAcServiceImpl;", "Lcom/algoroadmap/domain/service/SolvedAcService;", "solvedAcApiClient", "Lcom/algoroadmap/infrastructure/external/SolvedAcApiClient;", "(Lcom/algoroadmap/infrastructure/external/SolvedAcApiClient;)V", "fetchUserData", "Lcom/algoroadmap/domain/service/SolvedAcUserData;", "handle", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchUserSolvedProblems", "", "Lcom/algoroadmap/domain/service/SolvedAcProblemData;", "mapLevelToDifficulty", "level", "", "infrastructure"})
public class SolvedAcServiceImpl implements com.algoroadmap.domain.service.SolvedAcService {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.external.SolvedAcApiClient solvedAcApiClient = null;
    
    public SolvedAcServiceImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.external.SolvedAcApiClient solvedAcApiClient) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object fetchUserData(@org.jetbrains.annotations.NotNull()
    java.lang.String handle, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.algoroadmap.domain.service.SolvedAcUserData> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object fetchUserSolvedProblems(@org.jetbrains.annotations.NotNull()
    java.lang.String handle, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.algoroadmap.domain.service.SolvedAcProblemData>> $completion) {
        return null;
    }
    
    private java.lang.String mapLevelToDifficulty(int level) {
        return null;
    }
}