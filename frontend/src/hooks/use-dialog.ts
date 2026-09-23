import { createContext, useContext } from 'react';

export interface DialogContextValue {
  isOpen: boolean;
  open: () => void;
  close: () => void;
}

export const DialogContext = createContext<DialogContextValue | null>(null);

export function useDialog(): DialogContextValue {
  const context = useContext(DialogContext);
  if (!context) {
    throw new Error('useDialog must be used within a Dialog');
  }
  return context;
}
