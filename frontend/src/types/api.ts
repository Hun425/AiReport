// API Response Types
export interface ApiResponse<T> {
  data?: T;
  error?: ApiError;
  pagination?: PaginationInfo;
}

export interface ApiError {
  code: string;
  message: string;
  details?: string;
}

export interface PaginationInfo {
  page: number;
  size: number;
  total: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
}

// User Types
export interface User {
  id: number;
  email: string;
  name?: string; // Google OAuth에서 받아올 수 있는 사용자 이름
  solvedAcHandle: string | null;
  profileImageUrl: string | null;
  solvedAcClass: number;
  solvedCount: number;
  rank: number;
  lastSyncedAt: string | null;
  subscription: {
    plan: 'free' | 'premium';
    dailyReviewLimit: number;
    dailyReviewUsed: number;
  };
}

// Company Types
export interface CompanyInfo {
  id: number;
  name: string;
  logoUrl: string | null;
  description: string | null;
}

export interface CompanyListResponse {
  data: CompanyInfo[];
  pagination: PaginationInfo;
}

// Dashboard Types
export interface DashboardResponse {
  userInfo: DashboardUserInfo;
  tagAnalysis: TagAnalysisData[];
  recentReviews: RecentReviewData[];
}

export interface DashboardUserInfo {
  solvedAcHandle: string;
  profileImageUrl: string | null;
  solvedAcClass: number;
  solvedCount: number;
}

export interface TagAnalysisData {
  tag: string;
  solved: number;
  total: number;
  percentage: number;
}

export interface RecentReviewData {
  reviewId: number;
  problemId: number;
  problemTitle: string;
  createdAt: string;
}

// Roadmap Types
export interface CreateRoadmapRequest {
  companyId: number;
  durationInMonths: number;
}

export interface CreateRoadmapResponse {
  roadmapId: number;
  message: string;
}

export interface RoadmapDetailResponse {
  roadmapId: number;
  companyName: string;
  durationInMonths: number;
  totalProgress: number;
  createdAt: string;
  weeks: RoadmapWeekInfo[];
}

export interface RoadmapWeekInfo {
  weekNumber: number;
  title: string;
  progress: number;
  totalProblems: number;
  solvedProblems: number;
  problems: RoadmapProblemInfo[];
}

export interface RoadmapProblemInfo {
  problemId: number;
  title: string;
  difficulty: string;
  isSolved: boolean;
  hasReview: boolean;
  bojUrl: string;
}

// Sync Types
export interface SyncStatusResponse {
  status: 'idle' | 'in_progress' | 'completed' | 'failed';
  progress: number;
  message: string;
  startedAt: string | null;
  completedAt: string | null;
}