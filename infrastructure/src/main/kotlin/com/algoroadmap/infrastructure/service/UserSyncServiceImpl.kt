package com.algoroadmap.infrastructure.service

import com.algoroadmap.domain.entity.User
import com.algoroadmap.domain.entity.UserSolvedProblem  
import com.algoroadmap.domain.entity.Problem
import com.algoroadmap.domain.repository.UserRepository
import com.algoroadmap.domain.repository.UserSolvedProblemRepository
import com.algoroadmap.domain.repository.ProblemRepository
import com.algoroadmap.domain.service.SolvedAcService
import com.algoroadmap.domain.service.UserSyncService
import com.algoroadmap.domain.service.UserSyncService.SyncStatus
import com.algoroadmap.domain.service.UserSyncService.SyncStatusType
import com.algoroadmap.domain.service.UserSyncService.SyncResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class UserSyncServiceImpl(
    private val userRepository: UserRepository,
    private val userSolvedProblemRepository: UserSolvedProblemRepository,
    private val problemRepository: ProblemRepository,
    private val solvedAcService: SolvedAcService
) : UserSyncService {
    
    private val logger = LoggerFactory.getLogger(UserSyncServiceImpl::class.java)
    
    // 동기화 상태를 메모리에서 관리 (실제로는 Redis나 DB에 저장)
    private val syncStatusMap = ConcurrentHashMap<Long, SyncStatus>()
    
    override suspend fun startUserDataSync(userId: Long): SyncResult {
        return try {
            // 동기화 상태 초기화
            syncStatusMap[userId] = SyncStatus(
                status = SyncStatusType.IN_PROGRESS,
                progress = 0,
                message = "동기화를 시작합니다...",
                startedAt = LocalDateTime.now(),
                completedAt = null
            )
            
            // 비동기로 실제 동기화 작업 수행
            CoroutineScope(Dispatchers.IO).launch {
                performUserDataSync(userId)
            }
            
            SyncResult(
                isSuccess = true,
                message = "동기화가 시작되었습니다.",
                syncedProblemsCount = 0
            )
            
        } catch (e: Exception) {
            logger.error("동기화 시작 실패: userId=$userId", e)
            syncStatusMap[userId] = SyncStatus(
                status = SyncStatusType.FAILED,
                progress = 0,
                message = "동기화 시작에 실패했습니다: ${e.message}",
                startedAt = LocalDateTime.now(),
                completedAt = LocalDateTime.now()
            )
            
            SyncResult(
                isSuccess = false,
                message = "동기화 시작 실패",
                error = e.message
            )
        }
    }
    
    override fun getSyncStatus(userId: Long): SyncStatus {
        return syncStatusMap[userId] ?: SyncStatus(
            status = SyncStatusType.IDLE,
            progress = 0,
            message = "동기화 대기 중",
            startedAt = null,
            completedAt = null
        )
    }
    
    override fun isSyncInProgress(userId: Long): Boolean {
        return syncStatusMap[userId]?.status == SyncStatusType.IN_PROGRESS
    }
    
    /**
     * 실제 동기화 작업 수행 (비동기)
     */
    private suspend fun performUserDataSync(userId: Long) {
        try {
            val user = userRepository.findById(userId) ?: return
            
            logger.info("사용자 데이터 동기화 시작: userId=$userId, handle=${user.solvedAcHandle}")
            
            // 1단계: 사용자 기본 정보 업데이트
            updateSyncStatus(userId, 20, "사용자 정보를 업데이트하는 중...")
            updateUserData(user)
            
            // 2단계: 풀어온 문제 목록 조회
            updateSyncStatus(userId, 40, "풀어온 문제 목록을 조회하는 중...")
            val solvedProblems = solvedAcService.fetchUserSolvedProblems(user.solvedAcHandle)
            logger.info("조회된 문제 수: ${solvedProblems.size}개")
            
            // 3단계: 데이터베이스에 저장
            updateSyncStatus(userId, 70, "문제 데이터를 저장하는 중...")
            val savedCount = saveUserSolvedProblems(userId, solvedProblems)
            
            // 4단계: 완료
            updateSyncStatus(userId, 100, "동기화가 완료되었습니다.")
            syncStatusMap[userId] = SyncStatus(
                status = SyncStatusType.COMPLETED,
                progress = 100,
                message = "${savedCount}개의 문제가 동기화되었습니다.",
                startedAt = syncStatusMap[userId]?.startedAt,
                completedAt = LocalDateTime.now()
            )
            
            logger.info("사용자 데이터 동기화 완료: userId=$userId, syncedProblems=$savedCount")
            
        } catch (e: Exception) {
            logger.error("사용자 데이터 동기화 실패: userId=$userId", e)
            syncStatusMap[userId] = SyncStatus(
                status = SyncStatusType.FAILED,
                progress = 0,
                message = "동기화에 실패했습니다: ${e.message}",
                startedAt = syncStatusMap[userId]?.startedAt,
                completedAt = LocalDateTime.now()
            )
        }
    }
    
    private fun updateSyncStatus(userId: Long, progress: Int, message: String) {
        val currentStatus = syncStatusMap[userId]
        if (currentStatus != null) {
            syncStatusMap[userId] = currentStatus.copy(
                progress = progress,
                message = message
            )
        }
    }
    
    private suspend fun updateUserData(user: User) {
        val latestUserData = solvedAcService.fetchUserData(user.solvedAcHandle)
        
        if (latestUserData != null) {
            user.profileImageUrl = latestUserData.profileImageUrl
            user.solvedAcClass = latestUserData.solvedAcClass
            user.solvedCount = latestUserData.solvedCount
            user.rank = latestUserData.rank
            user.updatedAt = LocalDateTime.now()
            
            userRepository.save(user)
        }
    }
    
    private fun saveUserSolvedProblems(userId: Long, problems: List<com.algoroadmap.domain.service.SolvedAcProblemData>): Int {
        var savedCount = 0
        
        val user = userRepository.findById(userId) ?: return 0
        
        problems.forEach { problemData ->
            val existingProblem = userSolvedProblemRepository.findByUserIdAndProblemId(userId, problemData.problemId)
            
            if (existingProblem == null) {
                // Problem 엔티티 생성 또는 조회
                val problem = findOrCreateProblem(problemData)
                
                val userSolvedProblem = UserSolvedProblem(
                    user = user,
                    problem = problem,
                    solvedAt = LocalDateTime.now() // 실제로는 solved.ac에서 제공하는 날짜 사용
                )
                
                userSolvedProblemRepository.save(userSolvedProblem)
                savedCount++
            }
        }
        
        return savedCount
    }
    
    private fun findOrCreateProblem(problemData: com.algoroadmap.domain.service.SolvedAcProblemData): Problem {
        // 기존 Problem 조회 시도
        val existingProblem = problemRepository.findById(problemData.problemId)
        if (existingProblem != null) {
            return existingProblem
        }
        
        // 새 Problem 생성
        val newProblem = Problem(
            id = problemData.problemId,
            title = problemData.title,
            difficulty = problemData.difficulty,
            tags = problemData.tags.toMutableSet()
        )
        
        return problemRepository.save(newProblem)
    }
}