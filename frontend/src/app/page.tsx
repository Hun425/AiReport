'use client';

import { useAuthStore } from '@/stores/auth';
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/Button';
import { BookOpen, Target, Brain, TrendingUp } from 'lucide-react';

export default function Home() {
  const { isAuthenticated, isLoading, login } = useAuthStore();
  const router = useRouter();

  useEffect(() => {
    if (isAuthenticated && !isLoading) {
      router.push('/dashboard');
    }
  }, [isAuthenticated, isLoading, router]);

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {/* Hero Section */}
      <div className="container mx-auto px-6 py-16">
        <div className="text-center mb-16">
          <h1 className="text-5xl font-bold text-gray-900 mb-6">
            AI 알고리즘 학습 로드맵
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
            solved.ac 데이터를 기반으로 개인화된 학습 로드맵을 생성하고,
            AI가 제공하는 코드 리뷰로 실력을 향상시키세요.
          </p>
          <Button
            onClick={login}
            disabled={isLoading}
            size="lg"
            className="bg-blue-600 hover:bg-blue-700 text-white px-8 py-4 text-lg"
          >
            Google로 시작하기
          </Button>
        </div>

        {/* Features */}
        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8 mb-16">
          <FeatureCard
            icon={<Target className="h-8 w-8" />}
            title="맞춤형 로드맵"
            description="목표 기업과 현재 실력을 분석하여 개인화된 학습 계획을 제공합니다."
          />
          <FeatureCard
            icon={<BookOpen className="h-8 w-8" />}
            title="체계적 학습"
            description="약점을 파악하고 단계별로 실력을 향상시킬 수 있는 커리큘럼을 제공합니다."
          />
          <FeatureCard
            icon={<Brain className="h-8 w-8" />}
            title="AI 코드 리뷰"
            description="AI가 코드의 품질과 효율성을 분석하여 개선점을 제안합니다."
          />
          <FeatureCard
            icon={<TrendingUp className="h-8 w-8" />}
            title="실시간 진행률"
            description="학습 진행 상황을 실시간으로 추적하고 성취도를 확인할 수 있습니다."
          />
        </div>

        {/* CTA Section */}
        <div className="text-center bg-white rounded-lg shadow-lg p-8">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">
            지금 바로 시작해보세요
          </h2>
          <p className="text-gray-600 mb-6">
            무료로 시작하여 체계적인 알고리즘 학습을 경험해보세요.
          </p>
          <Button
            onClick={login}
            disabled={isLoading}
            size="lg"
            className="bg-blue-600 hover:bg-blue-700 text-white"
          >
            학습 시작하기
          </Button>
        </div>
      </div>
    </div>
  );
}

function FeatureCard({ 
  icon, 
  title, 
  description 
}: { 
  icon: React.ReactNode;
  title: string;
  description: string;
}) {
  return (
    <div className="bg-white rounded-lg shadow-lg p-6 text-center">
      <div className="text-blue-600 mb-4 flex justify-center">
        {icon}
      </div>
      <h3 className="text-xl font-semibold text-gray-900 mb-2">{title}</h3>
      <p className="text-gray-600">{description}</p>
    </div>
  );
}