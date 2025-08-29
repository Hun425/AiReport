'use client';

import { useAuthStore } from '@/stores/auth';
import { useRouter, usePathname } from 'next/navigation';
import { useEffect, ReactNode } from 'react';

interface AuthGuardProps {
  children: ReactNode;
}

// 보호되지 않은 (public) 경로들
const PUBLIC_PATHS = [
  '/',
  '/auth',
  '/health'
];

export function AuthGuard({ children }: AuthGuardProps) {
  const { isAuthenticated, isLoading, refreshUser } = useAuthStore();
  const router = useRouter();
  const pathname = usePathname();

  // 현재 경로가 public 경로인지 확인
  const isPublicPath = PUBLIC_PATHS.some(path => 
    pathname === path || pathname.startsWith(`${path}/`)
  );

  useEffect(() => {
    // 페이지 로드시 토큰이 있으면 사용자 정보 새로고침 시도
    const token = localStorage.getItem('accessToken');
    if (token && !isAuthenticated && !isLoading) {
      refreshUser();
    }
  }, [isAuthenticated, isLoading, refreshUser]);

  useEffect(() => {
    // 로딩 중이면 대기
    if (isLoading) return;

    // Public 경로는 인증 체크하지 않음
    if (isPublicPath) {
      // 인증된 사용자가 홈페이지에 접근하면 대시보드로 리다이렉트
      if (isAuthenticated && pathname === '/') {
        router.push('/dashboard');
      }
      return;
    }

    // Protected 경로인데 인증되지 않은 경우 홈으로 리다이렉트
    if (!isAuthenticated) {
      router.push('/');
      return;
    }
  }, [isAuthenticated, isLoading, pathname, isPublicPath, router]);

  // 로딩 중일 때 스피너 표시
  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  // Protected 경로인데 인증되지 않은 경우 빈 화면 (리다이렉트 처리 중)
  if (!isPublicPath && !isAuthenticated) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return <>{children}</>;
}