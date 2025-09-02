package com.algoroadmap.infrastructure.service;

@org.springframework.stereotype.Service()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0017\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fH\u0012J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0016J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00180\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\rH\u0012J(\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fH\u0012J2\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\b\b\u0002\u0010\u001f\u001a\u00020\u000eH\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/algoroadmap/infrastructure/service/DashboardServiceImpl;", "Lcom/algoroadmap/domain/service/DashboardService;", "userRepository", "Lcom/algoroadmap/domain/repository/UserRepository;", "userSolvedProblemRepository", "Lcom/algoroadmap/domain/repository/UserSolvedProblemRepository;", "codeReviewRepository", "Lcom/algoroadmap/domain/repository/CodeReviewRepository;", "problemRepository", "Lcom/algoroadmap/domain/repository/ProblemRepository;", "(Lcom/algoroadmap/domain/repository/UserRepository;Lcom/algoroadmap/domain/repository/UserSolvedProblemRepository;Lcom/algoroadmap/domain/repository/CodeReviewRepository;Lcom/algoroadmap/domain/repository/ProblemRepository;)V", "getDefaultTagTotalProblems", "", "", "", "getRecentReviews", "", "Lcom/algoroadmap/domain/service/RecentReviewResult;", "userId", "", "limit", "getUserDashboardInfo", "Lcom/algoroadmap/domain/service/DashboardUserResult;", "getUserTagAnalysis", "Lcom/algoroadmap/domain/service/TagAnalysisResult;", "mapToDisplayTag", "originalTag", "processTagTotalsForDisplay", "rawTagTotals", "processTagsForDisplay", "rawTagCounts", "maxDisplayTags", "infrastructure"})
public class DashboardServiceImpl implements com.algoroadmap.domain.service.DashboardService {
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.domain.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.domain.repository.UserSolvedProblemRepository userSolvedProblemRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.domain.repository.CodeReviewRepository codeReviewRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.algoroadmap.domain.repository.ProblemRepository problemRepository = null;
    
    public DashboardServiceImpl(@org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.repository.UserSolvedProblemRepository userSolvedProblemRepository, @org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.repository.CodeReviewRepository codeReviewRepository, @org.jetbrains.annotations.NotNull()
    com.algoroadmap.domain.repository.ProblemRepository problemRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.Map<java.lang.String, com.algoroadmap.domain.service.TagAnalysisResult> getUserTagAnalysis(long userId) {
        return null;
    }
    
    /**
     * 실제 DB 태그 데이터를 표시용으로 그룹핑 (사용자 태그와 동일한 로직)
     */
    private java.util.Map<java.lang.String, java.lang.Integer> processTagTotalsForDisplay(java.util.Map<java.lang.String, java.lang.Integer> rawTagTotals) {
        return null;
    }
    
    /**
     * 기본 태그별 전체 문제 수 (DB 조회 실패 시 사용)
     */
    private java.util.Map<java.lang.String, java.lang.Integer> getDefaultTagTotalProblems() {
        return null;
    }
    
    /**
     * 원형 그래프 표시용 태그 처리
     * - 상위 8개 태그만 표시
     * - 나머지는 "기타"로 그룹핑
     * - 코딩테스트 주요 태그로 매핑
     */
    private java.util.Map<java.lang.String, java.lang.Integer> processTagsForDisplay(java.util.Map<java.lang.String, java.lang.Integer> rawTagCounts, int maxDisplayTags) {
        return null;
    }
    
    /**
     * solved.ac 태그를 표시용 태그로 매핑
     */
    private java.lang.String mapToDisplayTag(java.lang.String originalTag) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.algoroadmap.domain.service.RecentReviewResult> getRecentReviews(long userId, int limit) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.algoroadmap.domain.service.DashboardUserResult getUserDashboardInfo(long userId) {
        return null;
    }
}