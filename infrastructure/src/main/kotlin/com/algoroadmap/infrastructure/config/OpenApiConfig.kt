package com.algoroadmap.infrastructure.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("AI 알고리즘 학습 로드맵 서비스 API")
                    .description("solved.ac API를 활용한 개인화된 학습 로드맵 및 AI 코드 리뷰 서비스")
                    .version("v1.1")
                    .license(
                        License()
                            .name("MIT License")
                            .url("https://opensource.org/licenses/MIT")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("http://localhost:8080/api/v1")
                        .description("로컬 개발 서버"),
                    Server()
                        .url("https://api.algoroadmap.com/api/v1")
                        .description("프로덕션 서버")
                )
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        "cookieAuth",
                        SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .`in`(SecurityScheme.In.COOKIE)
                            .name("accessToken")
                            .description("JWT 토큰을 쿠키로 전송")
                    )
            )
            .addSecurityItem(
                SecurityRequirement()
                    .addList("cookieAuth")
            )
    }
}