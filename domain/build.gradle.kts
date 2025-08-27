// Domain 계층 - 비즈니스 로직 (순수 Kotlin)
dependencies {
    // 도메인 계층은 외부 의존성을 최소화
    // JPA 어노테이션만 필요할 경우를 위해 추가
    implementation("jakarta.persistence:jakarta.persistence-api")
}