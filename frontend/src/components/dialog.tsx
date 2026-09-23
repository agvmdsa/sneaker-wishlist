import { useEffect, useState, type ReactNode } from 'react';
import { createPortal } from 'react-dom';
import { DialogContext, useDialog } from '@/hooks/use-dialog';

interface DialogProps {
  children: ReactNode;
}

export function Dialog({ children }: DialogProps) {
  const [isOpen, setIsOpen] = useState(false);

  const value = {
    isOpen,
    open: () => setIsOpen(true),
    close: () => setIsOpen(false),
  };

  return <DialogContext.Provider value={value}>{children}</DialogContext.Provider>;
}

interface DialogContentProps {
  children: ReactNode;
}

function DialogContent({ children }: DialogContentProps) {
  const { isOpen, close } = useDialog();

  useEffect(() => {
    if (!isOpen) {
      return;
    }

    function handleKeyDown(event: KeyboardEvent) {
      if (event.key === 'Escape') {
        close();
      }
    }

    document.addEventListener('keydown', handleKeyDown);
    return () => document.removeEventListener('keydown', handleKeyDown);
  }, [isOpen, close]);

  if (!isOpen) {
    return null;
  }

  return createPortal(
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4" onClick={close}>
      <div
        role="dialog"
        aria-modal="true"
        onClick={(event) => event.stopPropagation()}
        className="w-full max-w-sm rounded-md border border-border bg-surface p-6 shadow-md"
      >
        {children}
      </div>
    </div>,
    document.body,
  );
}

Dialog.Content = DialogContent;
