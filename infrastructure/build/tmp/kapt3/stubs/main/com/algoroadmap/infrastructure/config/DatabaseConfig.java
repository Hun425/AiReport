package com.algoroadmap.infrastructure.config;

@org.springframework.context.annotation.Configuration()
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = {"com.algoroadmap.infrastructure.persistence"})
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = {"com.algoroadmap.domain.entity"})
@org.springframework.transaction.annotation.EnableTransactionManagement()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/algoroadmap/infrastructure/config/DatabaseConfig;", "", "()V", "infrastructure"})
public class DatabaseConfig {
    
    public DatabaseConfig() {
        super();
    }
}