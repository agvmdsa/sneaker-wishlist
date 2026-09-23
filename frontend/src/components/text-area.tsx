import { useId, type ComponentProps } from 'react';

interface TextAreaProps extends ComponentProps<'textarea'> {
  label: string;
  error?: string;
}

export function TextArea({ label, error, id, ...rest }: TextAreaProps) {
  const generatedId = useId();
  const textAreaId = id ?? generatedId;

  return (
    <div className="flex flex-col gap-1">
      <label className="text-sm text-text-muted" htmlFor={textAreaId}>
        {label}
      </label>
      <textarea
        id={textAreaId}
        className="border border-border rounded-md px-3 py-2 text-base bg-surface text-text shadow-sm transition-colors focus:outline-none focus:ring-2 focus:ring-primary/40 focus:ring-offset-1 focus:ring-offset-bg"
        {...rest}
      />
      {error && <span className="text-sm text-danger">{error}</span>}
    </div>
  );
}
