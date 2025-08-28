package com.algoroadmap.application.dto

import java.time.LocalDateTime

/**
 * 동기화 상태 응답 DTO
 */
data class SyncStatusResponse(
    val status: String,      // "idle" | "in_progress" | "completed" | "failed"
    val progress: Int,       // 0-100
    val message: String,
    val startedAt: LocalDateTime?,
    val completedAt: LocalDateTime?
)