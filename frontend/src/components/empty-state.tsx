interface EmptyStateProps {
  title: string;
  description?: string;
}

export function EmptyState({ title, description }: EmptyStateProps) {
  return (
    <div className="flex flex-col gap-1 text-sm text-text-muted">
      <p>{title}</p>
      {description && <p>{description}</p>}
    </div>
  );
}
