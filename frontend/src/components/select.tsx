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
        className="border border-border rounded px-3 py-2 text-base bg-surface text-text focus:outline-2 focus:outline-primary focus:outline-offset-1"
        {...rest}
      >
        {children}
      </select>
      {error && <span className="text-sm text-danger">{error}</span>}
    </div>
  );
}
