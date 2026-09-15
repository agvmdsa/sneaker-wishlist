import { useId, type ComponentProps } from 'react';

interface InputProps extends ComponentProps<'input'> {
  label: string;
  error?: string;
}

export function Input({ label, error, id, ...rest }: InputProps) {
  const generatedId = useId();
  const inputId = id ?? generatedId;

  return (
    <div className="flex flex-col gap-1">
      <label className="text-sm text-text-muted" htmlFor={inputId}>
        {label}
      </label>
      <input
        id={inputId}
        className="border border-border rounded px-3 py-2 text-base bg-surface text-text focus:outline-2 focus:outline-primary focus:outline-offset-1"
        {...rest}
      />
      {error && <span className="text-sm text-danger">{error}</span>}
    </div>
  );
}
