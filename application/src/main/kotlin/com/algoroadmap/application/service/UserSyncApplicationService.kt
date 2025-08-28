package com.algoroadmap.application.service

import com.algoroadmap.application.dto.SyncStatusResponse
import com.algoroadmap.domain.repository.UserRepository
import com.algoroadmap.domain.service.UserSyncService
import com.algoroadmap.domain.exception.DomainException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserSyncApplicationService(
    private val userRepository: UserRepository,
    private val userSyncService: UserSyncService
) {
    
    /**
     * 사용자 데이터 동기화 요청 처리
     */
    suspend fun requestUserSync(userId: Long): SyncRequestResponse {
        // 1. 사용자 존재 확인
        val user = userRepository.findById(userId)
            ?: throw DomainException.UserNotFoundException(userId)
            
        // 2. 이미 동기화가 진행 중인지 확인
        if (userSyncService.isSyncInProgress(userId)) {
            throw DomainException.SyncInProgressException()
        }
        
        // 3. 동기화 시작 (비동기)
        val syncResult = userSyncService.startUserDataSync(userId)
        
        return SyncRequestResponse(
            message = if (syncResult.isSuccess) 
                "데이터 동기화가 시작되었습니다." 
            else 
                "동기화 시작에 실패했습니다: ${syncResult.error}",
            estimatedDuration = "약 1-2분",
            isSuccess = syncResult.isSuccess
        )
    }
    
    /**
     * 동기화 상태 조회
     */
    fun getSyncStatus(userId: Long): SyncStatusResponse {
        val syncStatus = userSyncService.getSyncStatus(userId)
        
        return SyncStatusResponse(
            status = syncStatus.status.name.lowercase(),
            progress = syncStatus.progress,
            message = syncStatus.message,
            startedAt = syncStatus.startedAt,
            completedAt = syncStatus.completedAt
        )
    }
}

/**
 * 동기화 요청 응답
 */
data class SyncRequestResponse(
    val message: String,
    val estimatedDuration: String,
    val isSuccess: Boolean
)