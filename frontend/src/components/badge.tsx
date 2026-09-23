import type { ReactNode } from 'react';
import { cn } from '@/lib/cn';

interface BadgeProps {
  children: ReactNode;
  tone?: 'neutral' | 'info' | 'success' | 'danger';
}

const toneClasses = {
  neutral: 'bg-border text-text',
  info: 'bg-primary text-primary-contrast',
  success: 'bg-success text-primary-contrast',
  danger: 'bg-danger text-white',
} as const;

export function Badge({ children, tone = 'neutral' }: BadgeProps) {
  return (
    <span className={cn('inline-flex items-center rounded-md px-2 py-1 text-sm', toneClasses[tone])}>
      {children}
    </span>
  );
}
