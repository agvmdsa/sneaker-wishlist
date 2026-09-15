import type { ReactNode } from 'react';
import { cn } from '@/lib/cn';

interface BadgeProps {
  children: ReactNode;
  tone?: 'neutral' | 'info' | 'success' | 'danger';
}

const toneClasses = {
  neutral: 'bg-border text-text',
  info: 'bg-primary text-primary-contrast',
  success: 'bg-green-700 text-white',
  danger: 'bg-danger text-white',
} as const;

export function Badge({ children, tone = 'neutral' }: BadgeProps) {
  return (
    <span className={cn('inline-flex items-center rounded px-2 py-1 text-sm', toneClasses[tone])}>
      {children}
    </span>
  );
}
