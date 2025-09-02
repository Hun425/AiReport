package com.algoroadmap.infrastructure.persistence;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0014\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005H\'J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u00052\u000e\b\u0001\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\'\u00a8\u0006\u000b"}, d2 = {"Lcom/algoroadmap/infrastructure/persistence/ProblemJpaRepository;", "Lorg/springframework/data/jpa/repository/JpaRepository;", "Lcom/algoroadmap/domain/entity/Problem;", "", "countAllByTag", "", "", "", "findByTagsIn", "tags", "", "infrastructure"})
public abstract interface ProblemJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<com.algoroadmap.domain.entity.Problem, java.lang.Long> {
    
    @org.springframework.data.jpa.repository.Query(value = "\n        SELECT DISTINCT p FROM Problem p \n        JOIN p.tags t \n        WHERE t IN :tags\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.algoroadmap.domain.entity.Problem> findByTagsIn(@org.springframework.data.repository.query.Param(value = "tags")
    @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> tags);
    
    @org.springframework.data.jpa.repository.Query(value = "\n        SELECT tag, COUNT(*) as count\n        FROM Problem p \n        JOIN p.tags tag \n        GROUP BY tag\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<java.lang.Object[]> countAllByTag();
}