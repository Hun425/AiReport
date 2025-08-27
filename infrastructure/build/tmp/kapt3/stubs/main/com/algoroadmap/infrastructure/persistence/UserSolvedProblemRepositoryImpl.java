package com.algoroadmap.infrastructure.persistence;

@org.springframework.stereotype.Repository()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001a\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0016J\u001c\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00060\u000f2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bH\u0016J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/UserSolvedProblemRepositoryImpl;", "Lcom/algoroadmap/domain/repository/UserSolvedProblemRepository;", "userSolvedProblemJpaRepository", "Lcom/algoroadmap/infrastructure/persistence/UserSolvedProblemJpaRepository;", "(Lcom/algoroadmap/infrastructure/persistence/UserSolvedProblemJpaRepository;)V", "countByUserId", "", "userId", "", "findByUserId", "", "Lcom/algoroadmap/domain/entity/UserSolvedProblem;", "findByUserIdAndProblemId", "problemId", "findTagAnalysByUserId", "", "", "save", "userSolvedProblem", "saveAll", "userSolvedProblems", "infrastructure"})
public class UserSolvedProblemRepositoryImpl implements com.algoroadmap.domain.repository.UserSolvedProblemRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.persistence.UserSolvedProblemJpaRepository userSolvedProblemJpaRepository = null;
    
    public UserSolvedProblemRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.persistence.UserSolvedProblemJpaRepository userSolvedProblemJpaRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.entity.UserSolvedProblem save(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.entity.UserSolvedProblem userSolvedProblem) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.entity.UserSolvedProblem> saveAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.algoroadmap.domain.entity.UserSolvedProblem> userSolvedProblems) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.entity.UserSolvedProblem> findByUserId(long userId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.UserSolvedProblem findByUserIdAndProblemId(long userId, long problemId) {
        return null;
    }
    
    @java.lang.Override()
    public int countByUserId(long userId) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.Map<java.lang.String, java.lang.Integer> findTagAnalysByUserId(long userId) {
        return null;
    }
}