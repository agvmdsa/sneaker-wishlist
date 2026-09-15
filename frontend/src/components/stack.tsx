import type { ReactNode } from 'react';
import { cn } from '@/lib/cn';

interface StackProps {
  children: ReactNode;
  direction?: 'row' | 'column';
  gap?: 'sm' | 'md' | 'lg';
}

const gapClasses = {
  sm: 'gap-2',
  md: 'gap-4',
  lg: 'gap-8',
} as const;

export function Stack({ children, direction = 'column', gap = 'md' }: StackProps) {
  return (
    <div className={cn('flex', direction === 'row' ? 'flex-row' : 'flex-col', gapClasses[gap])}>
      {children}
    </div>
  );
}
