interface LoadingStateProps {
  message?: string;
}

export function LoadingState({ message = 'Loading...' }: LoadingStateProps) {
  return (
    <p role="status" className="text-sm text-text-muted">
      {message}
    </p>
  );
}
