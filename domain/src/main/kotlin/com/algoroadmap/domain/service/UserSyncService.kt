package com.algoroadmap.domain.service

/**
 * 사용자 데이터 동기화 서비스 도메인 인터페이스
 */
interface UserSyncService {
    
    /**
     * 동기화 상태 정보
     */
    data class SyncStatus(
        val status: SyncStatusType,
        val progress: Int,
        val message: String,
        val startedAt: java.time.LocalDateTime?,
        val completedAt: java.time.LocalDateTime?
    )
    
    /**
     * 동기화 상태 타입
     */
    enum class SyncStatusType {
        IDLE,           // 대기 중
        IN_PROGRESS,    // 진행 중  
        COMPLETED,      // 완료
        FAILED          // 실패
    }
    
    /**
     * 동기화 결과
     */
    data class SyncResult(
        val isSuccess: Boolean,
        val message: String,
        val syncedProblemsCount: Int = 0,
        val error: String? = null
    )
    
    /**
     * 사용자 데이터 동기화 시작
     */
    suspend fun startUserDataSync(userId: Long): SyncResult
    
    /**
     * 동기화 상태 조회
     */
    fun getSyncStatus(userId: Long): SyncStatus
    
    /**
     * 동기화 진행 여부 확인
     */
    fun isSyncInProgress(userId: Long): Boolean
}