import type { ReactNode } from 'react';

interface PageContainerProps {
  children: ReactNode;
}

export function PageContainer({ children }: PageContainerProps) {
  return <main className="max-w-[960px] mx-auto px-4 py-6">{children}</main>;
}
