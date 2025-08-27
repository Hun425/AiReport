package com.algoroadmap.infrastructure.persistence;

@org.springframework.stereotype.Repository()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u0018\u0010\u0005\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/CompanyRepositoryImpl;", "Lcom/algoroadmap/domain/repository/CompanyRepository;", "companyJpaRepository", "Lcom/algoroadmap/infrastructure/persistence/CompanyJpaRepository;", "(Lcom/algoroadmap/infrastructure/persistence/CompanyJpaRepository;)V", "findAll", "", "Lcom/algoroadmap/domain/entity/Company;", "Lcom/algoroadmap/domain/repository/CompanyPage;", "page", "", "size", "findById", "id", "", "save", "company", "infrastructure"})
public class CompanyRepositoryImpl implements com.algoroadmap.domain.repository.CompanyRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.infrastructure.persistence.CompanyJpaRepository companyJpaRepository = null;
    
    public CompanyRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.infrastructure.persistence.CompanyJpaRepository companyJpaRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.entity.Company save(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.entity.Company company) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.entity.Company findById(long id) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.entity.Company> findAll() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.algoroadmap.domain.repository.CompanyPage findAll(int page, int size) {
        return null;
    }
}