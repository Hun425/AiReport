package com.algoroadmap.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@ConfigurationProperties(prefix = "app.external-api.solved-ac")
data class SolvedAcProperties(
    val baseUrl: String = "https://solved.ac/api/v3",
    val timeout: Duration = Duration.ofSeconds(30),
    val retryCount: Int = 3
)

@Configuration
@EnableConfigurationProperties(SolvedAcProperties::class)
class WebClientConfig {
    
    @Bean
    fun solvedAcWebClient(
        properties: SolvedAcProperties,
        builder: WebClient.Builder
    ): WebClient {
        return builder
            .baseUrl(properties.baseUrl)
            .defaultHeaders { headers ->
                headers.add("User-Agent", "AlgoroadmapService/1.0")
            }
            .build()
    }
}