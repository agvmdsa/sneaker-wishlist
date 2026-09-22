import { createContext, useContext, useRef, useState, type ReactNode } from 'react';
import { useClickOutside } from '@/hooks/use-click-outside';

interface MenuContextValue {
  isOpen: boolean;
  open: () => void;
  close: () => void;
}

const MenuContext = createContext<MenuContextValue | null>(null);

function useMenuContext(): MenuContextValue {
  const context = useContext(MenuContext);
  if (!context) {
    throw new Error('Menu.Trigger, Menu.Content, and Menu.Item must be used within a Menu');
  }
  return context;
}

interface MenuProps {
  children: ReactNode;
}

export function Menu({ children }: MenuProps) {
  const [isOpen, setIsOpen] = useState(false);

  const value: MenuContextValue = {
    isOpen,
    open: () => setIsOpen(true),
    close: () => setIsOpen(false),
  };

  return (
    <MenuContext.Provider value={value}>
      <div className="relative inline-block">{children}</div>
    </MenuContext.Provider>
  );
}

interface MenuTriggerProps {
  children: ReactNode;
}

function MenuTrigger({ children }: MenuTriggerProps) {
  const { isOpen, open, close } = useMenuContext();

  return (
    <button type="button" onClick={() => (isOpen ? close() : open())}>
      {children}
    </button>
  );
}

interface MenuContentProps {
  children: ReactNode;
}

function MenuContent({ children }: MenuContentProps) {
  const { isOpen, close } = useMenuContext();
  const ref = useRef<HTMLDivElement | null>(null);

  useClickOutside(ref, close);

  if (!isOpen) {
    return null;
  }

  return (
    <div
      ref={ref}
      className="absolute right-0 mt-1 min-w-40 rounded-md border border-border bg-surface py-1 shadow-md"
    >
      {children}
    </div>
  );
}

interface MenuItemProps {
  onSelect: () => void;
  children: ReactNode;
}

function MenuItem({ onSelect, children }: MenuItemProps) {
  const { close } = useMenuContext();

  function handleClick() {
    onSelect();
    close();
  }

  return (
    <button
      type="button"
      onClick={handleClick}
      className="block w-full px-3 py-2 text-left text-sm text-text hover:bg-border"
    >
      {children}
    </button>
  );
}

Menu.Trigger = MenuTrigger;
Menu.Content = MenuContent;
Menu.Item = MenuItem;
