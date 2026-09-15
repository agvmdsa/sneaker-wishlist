interface EmptyStateProps {
  title: string;
  description?: string;
}

export function EmptyState({ title, description }: EmptyStateProps) {
  return (
    <div>
      <p>{title}</p>
      {description && <p>{description}</p>}
    </div>
  );
}
