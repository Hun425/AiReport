package com.algoroadmap.infrastructure.persistence;

@org.springframework.stereotype.Repository()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\r\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/UserRepositoryImpl;", "Lcom/algoroadmap/domain/repository/UserRepository;", "userJpaRepository", "Lcom/algoroadmap/infrastructure/persistence/UserJpaRepository;", "(Lcom/algoroadmap/infrastructure/persistence/UserJpaRepository;)V", "existsByHandle", "", "handle", "", "findByGoogleId", "Lcom/algoroadmap/domain/entity/User;", "googleId", "findByHandle", "findById", "id", "", "isSyncInProgress", "userId", "save", "user", "infrastructure"})
public class UserRepositoryImpl implements com.algoroadmap.domain.repository.UserRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.persistence.UserJpaRepository userJpaRepository = null;
    
    public UserRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.persistence.UserJpaRepository userJpaRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.entity.User save(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.entity.User user) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.User findById(long id) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.User findByHandle(@org.jetbrains.annotations.NotNull()
    java.lang.String handle) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.User findByGoogleId(@org.jetbrains.annotations.NotNull()
    java.lang.String googleId) {
        return null;
    }
    
    @java.lang.Override()
    public boolean existsByHandle(@org.jetbrains.annotations.NotNull()
    java.lang.String handle) {
        return false;
    }
    
    @java.lang.Override()
    public boolean isSyncInProgress(long userId) {
        return false;
    }
}