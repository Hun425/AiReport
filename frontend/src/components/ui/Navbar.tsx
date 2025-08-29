'use client';

import { useAuthStore } from '@/stores/auth';
import { Button } from './Button';
import { User, LogOut } from 'lucide-react';

export function Navbar() {
  const { user, isAuthenticated, logout } = useAuthStore();

  if (!isAuthenticated) {
    return null;
  }

  const handleLogout = async () => {
    try {
      await logout();
      // 로그아웃 후 홈페이지로 이동
      window.location.href = '/';
    } catch (error) {
      console.error('Logout failed:', error);
      // 로그아웃 실패해도 토큰 제거하고 홈으로 이동
      localStorage.removeItem('accessToken');
      window.location.href = '/';
    }
  };

  return (
    <nav className="bg-white shadow-sm border-b border-gray-200">
      <div className="container mx-auto px-6 py-4">
        <div className="flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <h1 className="text-xl font-bold text-gray-900">AI 알고리즘 학습</h1>
          </div>
          
          <div className="flex items-center space-x-4">
            {user && (
              <div className="flex items-center space-x-3">
                <div className="flex items-center space-x-2">
                  <User className="h-5 w-5 text-gray-600" />
                  <span className="text-sm text-gray-700">
                    {user.name || user.email}
                  </span>
                </div>
                <Button
                  onClick={handleLogout}
                  variant="outline"
                  size="sm"
                  className="flex items-center space-x-1"
                >
                  <LogOut className="h-4 w-4" />
                  <span>로그아웃</span>
                </Button>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}