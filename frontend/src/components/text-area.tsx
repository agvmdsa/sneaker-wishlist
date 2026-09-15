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
        className="border border-border rounded px-3 py-2 text-base bg-surface text-text focus:outline-2 focus:outline-primary focus:outline-offset-1"
        {...rest}
      />
      {error && <span className="text-sm text-danger">{error}</span>}
    </div>
  );
}
