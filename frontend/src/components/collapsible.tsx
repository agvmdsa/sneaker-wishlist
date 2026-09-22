import { useState, type ReactNode } from 'react';

interface CollapsibleProps {
  defaultExpanded?: boolean;
  children: (state: { isExpanded: boolean; toggle: () => void }) => ReactNode;
}

export function Collapsible({ defaultExpanded = false, children }: CollapsibleProps) {
  const [isExpanded, setIsExpanded] = useState(defaultExpanded);

  function toggle() {
    setIsExpanded((current) => !current);
  }

  return <>{children({ isExpanded, toggle })}</>;
}
