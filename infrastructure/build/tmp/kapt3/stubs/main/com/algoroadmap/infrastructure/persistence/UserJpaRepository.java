package com.algoroadmap.infrastructure.persistence;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\bf\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\t\u001a\u00020\u0007H&J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0012\u0010\u000b\u001a\u00020\u00052\b\b\u0001\u0010\f\u001a\u00020\u0003H\'\u00a8\u0006\r"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/UserJpaRepository;", "Lorg/springframework/data/jpa/repository/JpaRepository;", "Lcom/algoroadmap/domain/entity/User;", "", "existsBySolvedAcHandle", "", "handle", "", "findByGoogleId", "googleId", "findBySolvedAcHandle", "isSyncInProgress", "userId", "infrastructure"})
public abstract interface UserJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<com.algoroadmap.domain.entity.User, java.lang.Long> {
    
    @org.jetbrains.annotations.Nullable()
    public abstract com.algoroadmap.domain.entity.User findBySolvedAcHandle(@org.jetbrains.annotations.NotNull()
    java.lang.String handle);
    
    @org.jetbrains.annotations.Nullable()
    public abstract com.algoroadmap.domain.entity.User findByGoogleId(@org.jetbrains.annotations.NotNull()
    java.lang.String googleId);
    
    public abstract boolean existsBySolvedAcHandle(@org.jetbrains.annotations.NotNull()
    java.lang.String handle);
    
    @org.springframework.data.jpa.repository.Query(value = "SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId AND u.lastSyncedAt IS NULL")
    public abstract boolean isSyncInProgress(@org.springframework.data.repository.query.Param(value = "userId")
    long userId);
}