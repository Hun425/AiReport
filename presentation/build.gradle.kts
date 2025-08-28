// Presentation 계층 - 컨트롤러 계층 (REST API)
plugins {
    id("org.springframework.boot")
}

dependencies {
    // 다른 모든 계층 의존
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure"))
    
    // Spring Boot Web (로깅 포함)
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux") // WebClientResponseException
    
    // Jackson for JSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    
    // SpringDoc OpenAPI (Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    
    // Development tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}