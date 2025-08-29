import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { 
  User, 
  CompanyListResponse, 
  DashboardResponse, 
  CreateRoadmapRequest, 
  CreateRoadmapResponse, 
  RoadmapDetailResponse,
  SyncStatusResponse
} from '@/types/api';

// API Base Configuration
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

class ApiClient {
  private client: AxiosInstance;

  constructor() {
    this.client = axios.create({
      baseURL: API_BASE_URL,
      timeout: 30000,
      withCredentials: true, // JWT 쿠키를 위해
      headers: {
        'Content-Type': 'application/json',
      },
    });

    // Request interceptor
    this.client.interceptors.request.use(
      (config) => {
        // localStorage에서 JWT 토큰을 가져와서 Authorization 헤더에 추가
        const token = localStorage.getItem('accessToken');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    // Response interceptor
    this.client.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          // 인증 에러 처리
          window.location.href = '/auth/login';
        }
        return Promise.reject(error);
      }
    );
  }

  // Auth APIs
  async loginWithGoogle(): Promise<void> {
    window.location.href = `${API_BASE_URL}/auth/google`;
  }

  async logout(): Promise<void> {
    await this.client.post('/auth/logout');
  }

  // User APIs
  async getMe(): Promise<User> {
    const response = await this.client.get<User>('/users/me');
    return response.data;
  }

  async updateHandle(handle: string): Promise<void> {
    await this.client.put('/users/me/handle', { handle });
  }

  // Sync APIs
  async startSync(): Promise<void> {
    await this.client.post('/users/me/sync');
  }

  async getSyncStatus(): Promise<SyncStatusResponse> {
    const response = await this.client.get<SyncStatusResponse>('/users/me/sync/status');
    return response.data;
  }

  // Dashboard APIs
  async getDashboard(): Promise<DashboardResponse> {
    const response = await this.client.get<DashboardResponse>('/dashboard/me');
    return response.data;
  }

  // Company APIs
  async getCompanies(page = 1, size = 20): Promise<CompanyListResponse> {
    const response = await this.client.get<CompanyListResponse>('/companies', {
      params: { page, size }
    });
    return response.data;
  }

  // Roadmap APIs
  async createRoadmap(request: CreateRoadmapRequest): Promise<CreateRoadmapResponse> {
    const response = await this.client.post<CreateRoadmapResponse>('/roadmaps', request);
    return response.data;
  }

  async getMyRoadmap(week?: number): Promise<RoadmapDetailResponse> {
    const response = await this.client.get<RoadmapDetailResponse>('/roadmaps/me', {
      params: week ? { week } : {}
    });
    return response.data;
  }

  async getRoadmapById(roadmapId: number): Promise<RoadmapDetailResponse> {
    const response = await this.client.get<RoadmapDetailResponse>(`/roadmaps/${roadmapId}`);
    return response.data;
  }

  async deactivateRoadmap(roadmapId: number): Promise<void> {
    await this.client.delete(`/roadmaps/${roadmapId}`);
  }
}

// Singleton instance
export const apiClient = new ApiClient();