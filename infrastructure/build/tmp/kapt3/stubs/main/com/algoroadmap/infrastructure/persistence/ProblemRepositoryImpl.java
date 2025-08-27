package com.algoroadmap.infrastructure.persistence;

@org.springframework.stereotype.Repository()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\nH\u0016J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/ProblemRepositoryImpl;", "Lcom/algoroadmap/domain/repository/ProblemRepository;", "problemJpaRepository", "Lcom/algoroadmap/infrastructure/persistence/ProblemJpaRepository;", "(Lcom/algoroadmap/infrastructure/persistence/ProblemJpaRepository;)V", "findById", "Lcom/algoroadmap/domain/entity/Problem;", "id", "", "findByTags", "", "tags", "", "save", "problem", "saveAll", "problems", "infrastructure"})
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
    public java.util.List<com.algoroadmap.domain.entity.Problem> saveAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.algoroadmap.domain.entity.Problem> problems) {
        return null;
    }
}