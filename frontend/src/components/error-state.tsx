interface ErrorStateProps {
  message: string;
  onRetry?: () => void;
}

export function ErrorState({ message, onRetry }: ErrorStateProps) {
  return (
    <div role="alert" className="flex flex-col gap-2 text-sm text-danger">
      <p>{message}</p>
      {onRetry && (
        <button
          onClick={onRetry}
          className="self-start cursor-pointer font-medium underline hover:no-underline"
        >
          Try again
        </button>
      )}
    </div>
  );
}
