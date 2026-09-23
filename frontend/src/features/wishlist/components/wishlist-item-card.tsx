import { Link } from 'react-router-dom';
import { Badge } from '@/components/badge';
import type { WishlistItem } from '../types/wishlist-item.schema';
import { statusTone } from '../utils/status-tone';

interface WishlistItemCardProps {
  item: WishlistItem;
}

export function WishlistItemCard({ item }: WishlistItemCardProps) {
  return (
    <Link
      to={`/wishlist/${item.id}`}
      className="flex items-center gap-4 border border-border rounded-md bg-surface p-4 shadow-sm transition-colors hover:bg-border/20"
    >
      {item.imageUrl && (
        <img src={item.imageUrl} alt={item.name} className="w-16 h-16 object-contain" />
      )}
      <div className="flex-1">
        <p className="font-medium">{item.name}</p>
        <p className="text-sm text-text-muted">
          {item.brand} · size {item.size}
        </p>
      </div>
      <Badge tone={statusTone(item.status)}>{item.status}</Badge>
    </Link>
  );
}
