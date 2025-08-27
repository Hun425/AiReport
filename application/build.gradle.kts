// Application 계층 - 애플리케이션 서비스 (유스케이스 구현)
dependencies {
    // 도메인 계층 의존
    implementation(project(":domain"))
    
    // Spring Context (DI 컨테이너)
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}