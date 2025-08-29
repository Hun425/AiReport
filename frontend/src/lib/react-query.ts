import { QueryClient } from '@tanstack/react-query';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 3,
      staleTime: 5 * 60 * 1000, // 5 minutes
      refetchOnWindowFocus: false,
    },
    mutations: {
      retry: 1,
    },
  },
});

// Query keys
export const queryKeys = {
  user: {
    me: ['user', 'me'] as const,
  },
  sync: {
    status: ['sync', 'status'] as const,
  },
  dashboard: {
    all: ['dashboard'] as const,
    me: ['dashboard', 'me'] as const,
  },
  companies: {
    all: ['companies'] as const,
    list: (page: number, size: number) => ['companies', 'list', page, size] as const,
  },
  roadmaps: {
    all: ['roadmaps'] as const,
    me: ['roadmaps', 'me'] as const,
    myWeek: (week?: number) => ['roadmaps', 'me', week] as const,
    detail: (id: number) => ['roadmaps', 'detail', id] as const,
  },
} as const;