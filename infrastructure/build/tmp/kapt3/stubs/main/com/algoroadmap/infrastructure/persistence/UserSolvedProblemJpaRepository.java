package com.algoroadmap.infrastructure.persistence;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0003H&J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b2\u0006\u0010\u0006\u001a\u00020\u0003H&J\u001a\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003H&J\u001e\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\b2\b\b\u0001\u0010\u0006\u001a\u00020\u0003H\'\u00a8\u0006\u000e"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/UserSolvedProblemJpaRepository;", "Lorg/springframework/data/jpa/repository/JpaRepository;", "Lcom/algoroadmap/domain/entity/UserSolvedProblem;", "", "countByUserId", "", "userId", "findByUserId", "", "findByUserIdAndProblemId", "problemId", "findTagAnalysByUserId", "", "", "infrastructure"})
public abstract interface UserSolvedProblemJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<com.algoroadmap.domain.entity.UserSolvedProblem, java.lang.Long> {
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.algoroadmap.domain.entity.UserSolvedProblem> findByUserId(long userId);
    
    @org.jetbrains.annotations.Nullable()
    public abstract com.algoroadmap.domain.entity.UserSolvedProblem findByUserIdAndProblemId(long userId, long problemId);
    
    public abstract int countByUserId(long userId);
    
    @org.springframework.data.jpa.repository.Query(value = "\n        SELECT tag, COUNT(*) as count\n        FROM UserSolvedProblem usp \n        JOIN usp.problem p \n        JOIN p.tags tag \n        WHERE usp.user.id = :userId\n        GROUP BY tag\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<java.lang.Object[]> findTagAnalysByUserId(@org.springframework.data.repository.query.Param(value = "userId")
    long userId);
}