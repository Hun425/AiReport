'use client';

import { useQuery } from '@tanstack/react-query';
import { apiClient } from '@/lib/api';
import { queryKeys } from '@/lib/react-query';
import { useAuthStore } from '@/stores/auth';
import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import { Button } from '@/components/ui/Button';
import { User, BarChart3, Target, Clock, Brain } from 'lucide-react';

export default function Dashboard() {
  const { isAuthenticated, user, isLoading: authLoading, refreshUser } = useAuthStore();
  const router = useRouter();
  const searchParams = useSearchParams();

  // 인증 확인
  useEffect(() => {
    if (!authLoading && !isAuthenticated) {
      refreshUser();
    }
  }, [authLoading, isAuthenticated, refreshUser]);

  const { data: dashboardData, isLoading, error } = useQuery({
    queryKey: queryKeys.dashboard.me,
    queryFn: () => apiClient.getDashboard(),
    enabled: isAuthenticated,
    retry: 1, // 한 번만 재시도
  });

  if (authLoading || isLoading) {
    return <DashboardSkeleton />;
  }

  if (error) {
    console.error('Dashboard API error:', error);
  }

  // 데이터가 없거나 오류가 있어도 기본 대시보드는 표시
  const hasData = dashboardData && !error;
  const userInfo = dashboardData?.userInfo;
  const tagAnalysis = dashboardData?.tagAnalysis || [];
  const recentReviews = dashboardData?.recentReviews || [];

  // 차트 색상
  const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884d8'];

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-6 py-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">대시보드</h1>
          <p className="text-gray-600">
            안녕하세요, {userInfo?.solvedAcHandle || user?.name || '사용자'}님!
          </p>
          {error && (
            <div className="mt-2 p-2 bg-yellow-50 border border-yellow-200 rounded">
              <p className="text-sm text-yellow-700">
                일부 데이터를 불러오는데 문제가 있습니다. 새로고침을 시도해보세요.
              </p>
            </div>
          )}
        </div>

        {!hasData ? (
          // 처음 사용자 또는 데이터 없음 상태
          <EmptyDashboard />
        ) : (
          // 정상 대시보드
          <NormalDashboard 
            userInfo={userInfo!} 
            tagAnalysis={tagAnalysis} 
            recentReviews={recentReviews}
            colors={COLORS}
          />
        )}
      </div>
    </div>
  );
}

// 빈 대시보드 컴포넌트
function EmptyDashboard() {
  const [handle, setHandle] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [step, setStep] = useState<'input' | 'sync' | 'completed'>('input');
  
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!handle.trim()) {
      setError('solved.ac 핸들을 입력해주세요.');
      return;
    }

    setIsSubmitting(true);
    setError('');

    try {
      // 1단계: 핸들 등록 및 유효성 검증
      await apiClient.updateHandle(handle.trim());
      setStep('sync');
    } catch (error: any) {
      console.error('Handle update failed:', error);
      if (error.response?.status === 400) {
        setError('존재하지 않는 solved.ac 핸들입니다.');
      } else {
        setError('핸들 등록 중 오류가 발생했습니다. 다시 시도해주세요.');
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleSync = async () => {
    setIsSubmitting(true);
    try {
      // 2단계: 데이터 동기화 (선택사항)
      await apiClient.startSync();
      setStep('completed');
      // 3초 후 새로고침으로 대시보드 데이터 로드
      setTimeout(() => {
        window.location.reload();
      }, 3000);
    } catch (error) {
      console.error('Sync failed:', error);
      setError('동기화 중 오류가 발생했습니다.');
    } finally {
      setIsSubmitting(false);
    }
  };

  const skipSync = () => {
    // 동기화 건너뛰고 바로 대시보드로
    window.location.reload();
  };

  return (
    <div className="text-center py-12">
{step === 'input' && (
        <div className="mb-8">
          <div className="mx-auto w-24 h-24 bg-blue-100 rounded-full flex items-center justify-center mb-4">
            <User className="h-12 w-12 text-blue-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-2">solved.ac 계정 연동</h2>
          <p className="text-gray-600 max-w-md mx-auto mb-6">
            solved.ac 핸들을 입력하여 계정을 연동해주세요.
          </p>
          
          <form onSubmit={handleSubmit} className="max-w-md mx-auto space-y-4">
            <div>
              <input
                type="text"
                value={handle}
                onChange={(e) => setHandle(e.target.value)}
                placeholder="solved.ac 핸들을 입력하세요 (예: goddold)"
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                disabled={isSubmitting}
              />
              {error && (
                <p className="text-sm text-red-600 mt-1">{error}</p>
              )}
            </div>
            
            <Button 
              type="submit"
              disabled={isSubmitting || !handle.trim()}
              className="w-full bg-blue-600 hover:bg-blue-700"
            >
              {isSubmitting ? '확인 중...' : '핸들 등록하기'}
            </Button>
          </form>
        </div>
      )}

      {step === 'sync' && (
        <div className="mb-8">
          <div className="mx-auto w-24 h-24 bg-green-100 rounded-full flex items-center justify-center mb-4">
            <Target className="h-12 w-12 text-green-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-2">핸들 등록 완료!</h2>
          <p className="text-gray-600 max-w-md mx-auto mb-6">
            <strong>{handle}</strong> 계정이 성공적으로 연동되었습니다.<br />
            추가 데이터 동기화를 진행하시겠습니까?
          </p>
          
          <div className="space-y-3 max-w-md mx-auto">
            <Button 
              onClick={handleSync}
              disabled={isSubmitting}
              className="w-full bg-green-600 hover:bg-green-700"
            >
              {isSubmitting ? '동기화 중...' : '풀어온 문제 데이터 동기화'}
            </Button>
            <Button 
              onClick={skipSync}
              variant="outline"
              className="w-full"
            >
              나중에 동기화
            </Button>
          </div>
          
          {error && (
            <p className="text-sm text-red-600 mt-4">{error}</p>
          )}
        </div>
      )}

      {step === 'completed' && (
        <div className="mb-8">
          <div className="mx-auto w-24 h-24 bg-purple-100 rounded-full flex items-center justify-center mb-4">
            <Brain className="h-12 w-12 text-purple-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-2">동기화 완료!</h2>
          <p className="text-gray-600 max-w-md mx-auto mb-6">
            데이터 동기화가 완료되었습니다.<br />
            잠시 후 대시보드가 업데이트됩니다...
          </p>
          <div className="animate-spin mx-auto w-8 h-8 border-4 border-purple-200 border-t-purple-600 rounded-full"></div>
        </div>
      )}

      {/* 기능 소개 */}
      <div className="grid md:grid-cols-3 gap-6 mt-12 max-w-4xl mx-auto">
        <div className="bg-white p-6 rounded-lg shadow-sm">
          <Target className="h-8 w-8 text-blue-600 mb-3" />
          <h3 className="font-semibold mb-2">맞춤형 로드맵</h3>
          <p className="text-sm text-gray-600">
            현재 실력과 목표를 분석하여 개인화된 학습 계획을 제공합니다.
          </p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-sm">
          <BarChart3 className="h-8 w-8 text-green-600 mb-3" />
          <h3 className="font-semibold mb-2">진행률 추적</h3>
          <p className="text-sm text-gray-600">
            학습 진행 상황을 실시간으로 확인하고 성취도를 시각화합니다.
          </p>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-sm">
          <Brain className="h-8 w-8 text-purple-600 mb-3" />
          <h3 className="font-semibold mb-2">AI 코드 리뷰</h3>
          <p className="text-sm text-gray-600">
            코드의 품질과 효율성을 분석하여 개선점을 제안합니다.
          </p>
        </div>
      </div>
    </div>
  );
}

// 정상 대시보드 컴포넌트  
function NormalDashboard({ userInfo, tagAnalysis, recentReviews, colors }: {
  userInfo: any;
  tagAnalysis: any[];
  recentReviews: any[];
  colors: string[];
}) {
  const router = useRouter();
  return (
    <div>
      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <StatCard
            icon={<User className="h-8 w-8" />}
            title="solved.ac 클래스"
            value={`${userInfo.solvedAcClass}급`}
            color="text-blue-600"
          />
          <StatCard
            icon={<BarChart3 className="h-8 w-8" />}
            title="해결한 문제"
            value={`${userInfo.solvedCount}문제`}
            color="text-green-600"
          />
          <StatCard
            icon={<Target className="h-8 w-8" />}
            title="최근 리뷰"
            value={`${recentReviews.length}개`}
            color="text-purple-600"
          />
          <StatCard
            icon={<Clock className="h-8 w-8" />}
            title="학습 진행률"
            value="75%"
            color="text-orange-600"
          />
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
          {/* 알고리즘 유형별 분석 */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold mb-4">알고리즘 유형별 분석</h2>
            <div className="h-64">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={tagAnalysis}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="tag" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="percentage" fill="#3B82F6" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>

          {/* 약점 분석 */}
          <div className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold mb-4">학습 현황</h2>
            <div className="h-64">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={tagAnalysis}
                    cx="50%"
                    cy="50%"
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="percentage"
                    label={({ tag, percentage }) => `${tag} ${percentage.toFixed(1)}%`}
                  >
                    {tagAnalysis.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={colors[index % colors.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>

        {/* 최근 코드 리뷰 */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-xl font-semibold">최근 코드 리뷰</h2>
            <Button variant="outline" size="sm">
              전체 보기
            </Button>
          </div>
          {recentReviews.length > 0 ? (
            <div className="space-y-4">
              {recentReviews.map((review) => (
                <div key={review.reviewId} className="border-l-4 border-blue-500 pl-4">
                  <h3 className="font-medium text-gray-900">{review.problemTitle}</h3>
                  <p className="text-sm text-gray-500">
                    문제 #{review.problemId} • {new Date(review.createdAt).toLocaleDateString('ko-KR')}
                  </p>
                </div>
              ))}
            </div>
          ) : (
            <p className="text-gray-500">아직 코드 리뷰가 없습니다.</p>
          )}
        </div>

        {/* 로드맵 생성 CTA */}
        <div className="bg-gradient-to-r from-blue-500 to-purple-600 rounded-lg shadow p-8 text-center text-white">
          <h2 className="text-2xl font-bold mb-4">맞춤형 학습 로드맵을 만들어보세요!</h2>
          <p className="mb-6">
            목표 기업을 선택하고 개인화된 알고리즘 학습 계획을 받아보세요.
          </p>
          <Button
            onClick={() => router.push('/roadmap/create')}
            className="bg-white text-blue-600 hover:bg-gray-100"
            size="lg"
          >
            로드맵 생성하기
          </Button>
        </div>
    </div>
  );
}

function StatCard({ 
  icon, 
  title, 
  value, 
  color 
}: { 
  icon: React.ReactNode;
  title: string;
  value: string;
  color: string;
}) {
  return (
    <div className="bg-white rounded-lg shadow p-6">
      <div className={`${color} mb-2`}>
        {icon}
      </div>
      <h3 className="text-sm font-medium text-gray-500 mb-1">{title}</h3>
      <p className="text-2xl font-bold text-gray-900">{value}</p>
    </div>
  );
}

function DashboardSkeleton() {
  return (
    <div className="min-h-screen bg-gray-50">
      <div className="container mx-auto px-6 py-8">
        <div className="animate-pulse">
          {/* Header skeleton */}
          <div className="mb-8">
            <div className="h-8 bg-gray-300 rounded w-48 mb-2"></div>
            <div className="h-4 bg-gray-300 rounded w-32"></div>
          </div>

          {/* Stats cards skeleton */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            {[1, 2, 3, 4].map((i) => (
              <div key={i} className="bg-white rounded-lg shadow p-6">
                <div className="h-8 bg-gray-300 rounded w-8 mb-2"></div>
                <div className="h-4 bg-gray-300 rounded w-24 mb-2"></div>
                <div className="h-6 bg-gray-300 rounded w-16"></div>
              </div>
            ))}
          </div>

          {/* Charts skeleton */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
            <div className="bg-white rounded-lg shadow p-6">
              <div className="h-6 bg-gray-300 rounded w-48 mb-4"></div>
              <div className="h-64 bg-gray-200 rounded"></div>
            </div>
            <div className="bg-white rounded-lg shadow p-6">
              <div className="h-6 bg-gray-300 rounded w-32 mb-4"></div>
              <div className="h-64 bg-gray-200 rounded"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}