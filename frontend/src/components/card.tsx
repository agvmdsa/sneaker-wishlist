import type { ComponentProps } from 'react';
import { cn } from '@/lib/cn';

export function Card({ className, ...rest }: ComponentProps<'div'>) {
  return (
    <div className={cn('rounded-md border border-border bg-surface p-4 shadow-sm', className)} {...rest} />
  );
}
