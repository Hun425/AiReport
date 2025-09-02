'use client';

import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { queryClient } from '@/lib/react-query';
import { useEffect } from 'react';
import { useAuthStore } from '@/stores/auth';

export function Providers({ children }: { children: React.ReactNode }) {
  const refreshUser = useAuthStore((state) => state.refreshUser);

  useEffect(() => {
    // 앱 시작 시 사용자 정보 확인 (쿠키 기반)
    refreshUser().catch(() => {
      // 인증되지 않은 사용자는 무시
      console.debug('User not authenticated');
    });
  }, [refreshUser]);

  return (
    <QueryClientProvider client={queryClient}>
      {children}
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}