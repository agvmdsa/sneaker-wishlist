import type { ComponentProps } from 'react';
import { cn } from '@/lib/cn';

interface ButtonProps extends ComponentProps<'button'> {
  variant?: 'primary' | 'secondary' | 'danger';
}

const variantClasses = {
  primary: 'bg-primary text-primary-contrast',
  secondary: 'bg-surface border-border text-text',
  danger: 'bg-danger text-primary-contrast',
} as const;

export function Button({ variant = 'primary', className, ...rest }: ButtonProps) {
  return (
    <button
      className={cn(
        'inline-flex items-center justify-center rounded border border-transparent px-4 py-2 cursor-pointer disabled:cursor-not-allowed disabled:opacity-60',
        variantClasses[variant],
        className,
      )}
      {...rest}
    />
  );
}
