package com.algoroadmap.infrastructure.persistence;

@org.springframework.stereotype.Repository()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\bH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/CodeReviewRepositoryImpl;", "Lcom/algoroadmap/domain/repository/CodeReviewRepository;", "codeReviewJpaRepository", "Lcom/algoroadmap/infrastructure/persistence/CodeReviewJpaRepository;", "(Lcom/algoroadmap/infrastructure/persistence/CodeReviewJpaRepository;)V", "countByUserIdAndCreatedAtAfter", "", "userId", "", "date", "Ljava/time/LocalDateTime;", "findById", "Lcom/algoroadmap/domain/entity/CodeReview;", "id", "findByUserIdOrderByCreatedAtDesc", "Lcom/algoroadmap/domain/repository/CodeReviewPage;", "page", "size", "save", "codeReview", "infrastructure"})
public class CodeReviewRepositoryImpl implements com.algoroadmap.domain.repository.CodeReviewRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.persistence.CodeReviewJpaRepository codeReviewJpaRepository = null;
    
    public CodeReviewRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.persistence.CodeReviewJpaRepository codeReviewJpaRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.entity.CodeReview save(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.entity.CodeReview codeReview) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.CodeReview findById(long id) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.repository.CodeReviewPage findByUserIdOrderByCreatedAtDesc(long userId, int page, int size) {
        return null;
    }
    
    @java.lang.Override()
    public int countByUserIdAndCreatedAtAfter(long userId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime date) {
        return 0;
    }
}