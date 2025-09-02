package com.algoroadmap.infrastructure.persistence;

@org.springframework.stereotype.Repository()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\n\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0014\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J&\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u0016J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fH\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/ProblemRepositoryImpl;", "Lcom/algoroadmap/domain/repository/ProblemRepository;", "problemJpaRepository", "Lcom/algoroadmap/infrastructure/persistence/ProblemJpaRepository;", "(Lcom/algoroadmap/infrastructure/persistence/ProblemJpaRepository;)V", "countAll", "", "countAllByTag", "", "", "", "findById", "Lcom/algoroadmap/domain/entity/Problem;", "id", "findByTagAndDifficulty", "", "tag", "difficultyLevel", "limit", "findByTags", "tags", "save", "problem", "saveAll", "problems", "infrastructure"})
public class ProblemRepositoryImpl implements com.algoroadmap.domain.repository.ProblemRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.persistence.ProblemJpaRepository problemJpaRepository = null;
    
    public ProblemRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.persistence.ProblemJpaRepository problemJpaRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.entity.Problem save(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.entity.Problem problem) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.Problem findById(long id) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.entity.Problem> findByTags(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tags) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.entity.Problem> findByTagAndDifficulty(@org.jetbrains.annotations.NotNull()
    java.lang.String tag, @org.jetbrains.annotations.NotNull()
    java.lang.String difficultyLevel, int limit) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.entity.Problem> saveAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.algoroadmap.domain.entity.Problem> problems) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.Map<java.lang.String, java.lang.Integer> countAllByTag() {
        return null;
    }
    
    @java.lang.Override()
    public long countAll() {
        return 0L;
    }
}