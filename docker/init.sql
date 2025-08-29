-- 알고로드맵 서비스 초기 데이터베이스 설정

-- 확장 기능 활성화
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 초기 회사 데이터 삽입
INSERT INTO companies (name, logo_url, description) VALUES
('카카오', NULL, '카카오 코딩테스트 대비 - DP, 그래프 탐색, 구현 중심'),
('네이버', NULL, '네이버 코딩테스트 대비 - 구현, 문자열, 자료구조 중심'),
('라인', NULL, '라인 코딩테스트 대비 - 그래프 탐색, DP 중심'),
('삼성전자', NULL, '삼성전자 SW역량테스트 대비 - 시뮬레이션, 백트래킹 중심'),
('LG전자', NULL, 'LG전자 코딩테스트 대비 - 구현, DP 중심'),
('토스', NULL, '토스 코딩테스트 대비 - 알고리즘 구현, 시스템 설계'),
('우아한형제들', NULL, '우아한형제들 코딩테스트 대비 - 구현, 자료구조'),
('쿠팡', NULL, '쿠팡 코딩테스트 대비 - 알고리즘, 자료구조'),
('NHN', NULL, 'NHN 코딩테스트 대비 - 구현, 문자열, DP'),
('SK', NULL, 'SK 코딩테스트 대비 - 종합 알고리즘 역량')
ON CONFLICT DO NOTHING;

-- 회사별 프로필 태그 데이터 삽입
INSERT INTO company_profile_tags (company_id, tag_name, weight) VALUES
-- 카카오
(1, 'DP', 3),
(1, '그래프 탐색', 3),
(1, '구현', 2),
(1, '문자열', 2),
(1, '그리디', 2),
-- 네이버
(2, '구현', 3),
(2, '문자열', 3),
(2, 'DP', 2),
(2, '자료구조', 2),
-- 라인
(3, '그래프 탐색', 3),
(3, 'DP', 2),
(3, '구현', 2),
-- 삼성전자
(4, '시뮬레이션', 3),
(4, '구현', 3),
(4, '백트래킹', 2),
(4, 'BFS', 2),
-- LG전자
(5, '구현', 2),
(5, 'DP', 2),
(5, '그래프 탐색', 2),
-- 토스
(6, '구현', 3),
(6, '자료구조', 3),
(6, 'DP', 2),
(6, '그리디', 2),
-- 우아한형제들
(7, '구현', 3),
(7, '자료구조', 2),
(7, '문자열', 2),
-- 쿠팡
(8, '자료구조', 3),
(8, '그래프 탐색', 2),
(8, '구현', 2),
-- NHN
(9, '구현', 2),
(9, '문자열', 2),
(9, 'DP', 2),
-- SK
(10, '구현', 2),
(10, 'DP', 2),
(10, '그래프 탐색', 2),
(10, '자료구조', 2)
ON CONFLICT DO NOTHING;

-- 인덱스 생성 (성능 최적화)
CREATE INDEX IF NOT EXISTS idx_users_handle ON users(solved_ac_handle);
CREATE INDEX IF NOT EXISTS idx_user_solved_problems_user_id ON user_solved_problems(user_id);
CREATE INDEX IF NOT EXISTS idx_user_solved_problems_problem_id ON user_solved_problems(problem_id);
CREATE INDEX IF NOT EXISTS idx_code_reviews_user_id ON code_reviews(user_id);
CREATE INDEX IF NOT EXISTS idx_code_reviews_created_at ON code_reviews(created_at);

-- 초기 샘플 문제 데이터 (선택사항)
INSERT INTO problems (id, title, difficulty) VALUES
(1000, 'A+B', 'Bronze 5'),
(2748, '피보나치 수 2', 'Silver 5'),
(1463, '1로 만들기', 'Silver 3'),
(2579, '계단 오르기', 'Silver 3'),
(1753, '최단경로', 'Gold 4'),
(11726, '2×n 타일링', 'Silver 3')
ON CONFLICT (id) DO NOTHING;

-- 문제 태그 데이터
INSERT INTO problem_tags (problem_id, tag) VALUES
(1000, '구현'),
(1000, '사칙연산'),
(2748, 'DP'),
(2748, '수학'),
(1463, 'DP'),
(2579, 'DP'),
(1753, '그래프 탐색'),
(1753, '다익스트라'),
(11726, 'DP')
ON CONFLICT DO NOTHING;

COMMIT;