import { Badge } from '@/components/badge';
import type { WishlistItem } from '../types/wishlist-item.schema';
import { statusTone } from '../utils/status-tone';

interface WishlistItemCardProps {
  item: WishlistItem;
}

export function WishlistItemCard({ item }: WishlistItemCardProps) {
  return (
    <div className="flex items-center gap-4 border border-border rounded p-4">
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
    </div>
  );
}
