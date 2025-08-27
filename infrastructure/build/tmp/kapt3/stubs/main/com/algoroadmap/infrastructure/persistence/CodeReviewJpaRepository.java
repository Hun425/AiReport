package com.algoroadmap.infrastructure.persistence;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&\u00a8\u0006\r"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/CodeReviewJpaRepository;", "Lorg/springframework/data/jpa/repository/JpaRepository;", "Lcom/algoroadmap/domain/entity/CodeReview;", "", "countByUserIdAndCreatedAtAfter", "", "userId", "date", "Ljava/time/LocalDateTime;", "findByUserIdOrderByCreatedAtDesc", "Lorg/springframework/data/domain/Page;", "pageable", "Lorg/springframework/data/domain/Pageable;", "infrastructure"})
public abstract interface CodeReviewJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<com.algoroadmap.domain.entity.CodeReview, java.lang.Long> {
    
    @org.jetbrains.annotations.NotNull()
    public abstract org.springframework.data.domain.Page<com.algoroadmap.domain.entity.CodeReview> findByUserIdOrderByCreatedAtDesc(long userId, @org.jetbrains.annotations.NotNull()
    org.springframework.data.domain.Pageable pageable);
    
    public abstract int countByUserIdAndCreatedAtAfter(long userId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDateTime date);
}