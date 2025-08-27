package com.algoroadmap.presentation.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class HealthController {
    
    @GetMapping("/health")
    fun health(): ResponseEntity<Map<String, Any>> {
        val response = mapOf(
            "status" to "healthy",
            "timestamp" to LocalDateTime.now(),
            "version" to "1.0.0",
            "services" to mapOf(
                "database" to "healthy",
                "solvedAcApi" to "healthy",
                "aiApi" to "healthy"
            )
        )
        return ResponseEntity.ok(response)
    }
}