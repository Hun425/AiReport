package com.algoroadmap.infrastructure.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = ["com.algoroadmap.infrastructure.persistence"])
@EntityScan(basePackages = ["com.algoroadmap.domain.entity"])
@EnableTransactionManagement
class DatabaseConfig