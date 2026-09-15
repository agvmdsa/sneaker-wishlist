import type { ReactNode } from 'react';
import { LoadingState } from './loading-state';
import { ErrorState } from './error-state';
import { EmptyState } from './empty-state';

interface AsyncStateProps {
  isLoading: boolean;
  error?: string | null;
  onRetry?: () => void;
  isEmpty?: boolean;
  emptyMessage?: string;
  children: ReactNode;
}

export function AsyncState({
  isLoading,
  error,
  onRetry,
  isEmpty,
  emptyMessage = 'Nothing to show yet.',
  children,
}: AsyncStateProps) {
  if (isLoading) {
    return <LoadingState />;
  }

  if (error) {
    return <ErrorState message={error} onRetry={onRetry} />;
  }

  if (isEmpty) {
    return <EmptyState title={emptyMessage} />;
  }

  return <>{children}</>;
}
