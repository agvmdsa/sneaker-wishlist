import { Badge } from '@/components/badge';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import type { WishlistItem } from '../types/wishlist-item.schema';
import { statusTone } from '../utils/status-tone';

interface WishlistItemSummaryProps {
  item: WishlistItem;
  onEdit: () => void;
}

export function WishlistItemSummary({ item, onEdit }: WishlistItemSummaryProps) {
  return (
    <Stack gap="md">
      <Stack direction="row" gap="md">
        {item.imageUrl && (
          <img src={item.imageUrl} alt={item.name} className="w-24 h-24 object-contain" />
        )}
        <div className="flex-1">
          <p className="font-medium">{item.name}</p>
          <p className="text-sm text-text-muted">
            {item.brand} · size {item.size}
          </p>
        </div>
        <Badge tone={statusTone(item.status)}>{item.status}</Badge>
      </Stack>

      {item.notes && <p className="text-sm text-text-muted">{item.notes}</p>}

      <Stack direction="row" gap="sm">
        <Button variant="secondary" onClick={onEdit}>
          Edit
        </Button>
      </Stack>
    </Stack>
  );
}
