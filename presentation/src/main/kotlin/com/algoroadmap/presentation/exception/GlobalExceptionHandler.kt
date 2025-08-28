package com.algoroadmap.presentation.exception

import com.algoroadmap.domain.exception.DomainException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.time.LocalDateTime

/**
 * 글로벌 예외 처리기
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    
    /**
     * 도메인 예외 처리
     */
    @ExceptionHandler(DomainException::class)
    fun handleDomainException(ex: DomainException): ResponseEntity<ApiErrorResponse> {
        logger.warn("도메인 예외 발생: {}", ex.message, ex)
        
        val errorCode = ErrorCode.fromDomainException(ex)
        val errorResponse = ApiErrorResponse.from(errorCode, ex.message)
        
        return ResponseEntity.status(errorCode.status).body(errorResponse)
    }
    
    /**
     * WebClient 응답 예외 처리 (외부 API 호출 오류)
     */
    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<ApiErrorResponse> {
        logger.error("외부 API 호출 오류: status={}, body={}", ex.statusCode, ex.responseBodyAsString, ex)
        
        val errorResponse = ApiErrorResponse.from(
            ErrorCode.EXTERNAL_API_ERROR, 
            details = "HTTP ${ex.statusCode.value()}: ${ex.statusText}"
        )
        
        return ResponseEntity.status(ErrorCode.EXTERNAL_API_ERROR.status).body(errorResponse)
    }
    
    /**
     * 유효성 검증 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        logger.warn("입력 데이터 검증 실패: {}", ex.message)
        
        val fieldErrors = ex.bindingResult.fieldErrors.map { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }
        
        val errorResponse = ApiErrorResponse.from(
            ErrorCode.VALIDATION_ERROR,
            details = fieldErrors.joinToString(", ")
        )
        
        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.status).body(errorResponse)
    }
    
    /**
     * 인증 실패 예외 처리
     */
    @ExceptionHandler(SecurityException::class)
    fun handleSecurityException(ex: SecurityException): ResponseEntity<ApiErrorResponse> {
        logger.warn("보안 예외 발생: {}", ex.message, ex)
        
        val errorResponse = ApiErrorResponse.from(ErrorCode.UNAUTHORIZED)
        
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.status).body(errorResponse)
    }
    
    /**
     * 일반 예외 처리
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ApiErrorResponse> {
        logger.error("예상치 못한 오류 발생: {}", ex.message, ex)
        
        val errorResponse = ApiErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR)
        
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.status).body(errorResponse)
    }
}

/**
 * API 에러 응답 구조
 */
data class ApiErrorResponse(
    val error: ApiError
) {
    companion object {
        fun from(errorCode: ErrorCode, customMessage: String? = null, details: String? = null): ApiErrorResponse {
            return ApiErrorResponse(
                error = ApiError(
                    code = errorCode.code,
                    message = customMessage ?: errorCode.message,
                    details = details,
                    timestamp = LocalDateTime.now()
                )
            )
        }
    }
}

/**
 * API 에러 정보
 */
data class ApiError(
    val code: String,
    val message: String,
    val details: String? = null,
    val timestamp: LocalDateTime
)