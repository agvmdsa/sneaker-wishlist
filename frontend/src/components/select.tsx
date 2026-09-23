import { useId, type ComponentProps } from 'react';

interface SelectProps extends ComponentProps<'select'> {
  label: string;
  error?: string;
}

export function Select({ label, error, id, children, ...rest }: SelectProps) {
  const generatedId = useId();
  const selectId = id ?? generatedId;

  return (
    <div className="flex flex-col gap-1">
      <label className="text-sm text-text-muted" htmlFor={selectId}>
        {label}
      </label>
      <select
        id={selectId}
        className="border border-border rounded-md px-3 py-2 text-base bg-surface text-text shadow-sm transition-colors focus:outline-none focus:ring-2 focus:ring-primary/40 focus:ring-offset-1 focus:ring-offset-bg"
        {...rest}
      >
        {children}
      </select>
      {error && <span className="text-sm text-danger">{error}</span>}
    </div>
  );
}
