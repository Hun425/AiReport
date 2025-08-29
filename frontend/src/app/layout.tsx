import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { Providers } from "./providers";
import { Navbar } from "@/components/ui/Navbar";
import { AuthGuard } from "@/components/auth/AuthGuard";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "AI 알고리즘 학습 로드맵",
  description: "개인화된 알고리즘 문제 해결 로드맵과 AI 코드 리뷰 서비스",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className={`${inter.className} antialiased`}>
        <Providers>
          <AuthGuard>
            <Navbar />
            {children}
          </AuthGuard>
        </Providers>
      </body>
    </html>
  );
}
