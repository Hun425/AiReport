// Presentation 계층 - 컨트롤러 계층 (REST API)
plugins {
    id("org.springframework.boot")
}

dependencies {
    // 다른 모든 계층 의존
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure"))
    
    // Spring Boot Web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
    // Jackson for JSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    
    // Development tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}