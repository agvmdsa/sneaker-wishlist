import type { ComponentProps } from 'react';
import { cn } from '@/lib/cn';

interface ButtonProps extends ComponentProps<'button'> {
  variant?: 'primary' | 'secondary' | 'danger';
}

const variantClasses = {
  primary: 'bg-primary text-primary-contrast hover:bg-primary/90 active:bg-primary/80',
  secondary: 'bg-surface border-border text-text hover:bg-border/40 active:bg-border/60',
  danger: 'bg-danger text-primary-contrast hover:bg-danger/90 active:bg-danger/80',
} as const;

export function Button({ variant = 'primary', className, ...rest }: ButtonProps) {
  return (
    <button
      className={cn(
        'inline-flex items-center justify-center rounded-md border border-transparent px-4 py-2 shadow-sm transition-colors cursor-pointer disabled:cursor-not-allowed disabled:opacity-60 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary/40 focus-visible:ring-offset-2 focus-visible:ring-offset-bg',
        variantClasses[variant],
        className,
      )}
      {...rest}
    />
  );
}
