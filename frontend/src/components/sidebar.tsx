import { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';
import { cn } from '@/lib/cn';

const SIDEBAR_STORAGE_KEY = 'sidebar-open';

const links = [
  { to: '/', label: 'Home' },
  { to: '/wishlist', label: 'Wishlist' },
  { to: '/search', label: 'Search' },
  { to: '/stats', label: 'Stats' },
];

export function Sidebar() {
  const [isOpen, setIsOpen] = useState(() => {
    const stored = localStorage.getItem(SIDEBAR_STORAGE_KEY);
    return stored === null ? true : stored === 'true';
  });

  useEffect(() => {
    localStorage.setItem(SIDEBAR_STORAGE_KEY, String(isOpen));
  }, [isOpen]);

  return (
    <aside
      className={cn(
        'relative border-r border-border flex flex-col shrink-0',
        isOpen ? 'w-48 p-4' : 'w-12 p-2',
      )}
    >
      <button
        type="button"
        onClick={() => setIsOpen((current) => !current)}
        className="absolute -right-3 top-4 w-6 h-6 rounded-full border border-border bg-surface flex items-center justify-center text-xs cursor-pointer"
        aria-label={isOpen ? 'Collapse sidebar' : 'Expand sidebar'}
      >
        {isOpen ? '‹' : '›'}
      </button>

      {isOpen && (
        <nav className="flex flex-col gap-2 mt-8">
          {links.map((link) => (
            <NavLink
              key={link.to}
              to={link.to}
              end
              className={({ isActive }) =>
                cn('text-sm', isActive ? 'font-semibold text-primary' : 'text-text-muted')
              }
            >
              {link.label}
            </NavLink>
          ))}
        </nav>
      )}
    </aside>
  );
}
