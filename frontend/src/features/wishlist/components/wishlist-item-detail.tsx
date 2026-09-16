import { useState } from 'react';
import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { useWishlistItem } from '../hooks/use-wishlist-item';
import { WishlistItemSummary } from './wishlist-item-summary';
import { EditWishlistItemForm } from './edit-wishlist-item-form';
import { StatusChangeControl } from './status-change-control';
import { TagManager } from './tag-manager';

interface WishlistItemDetailProps {
  id: number;
}

export function WishlistItemDetail({ id }: WishlistItemDetailProps) {
  const { data: item, isLoading, error, setItem } = useWishlistItem(id);
  const [isEditing, setIsEditing] = useState(false);

  return (
    <AsyncState isLoading={isLoading} error={error?.message}>
      {item && (
        <Stack gap="lg">
          {isEditing ? (
            <EditWishlistItemForm
              item={item}
              onSaved={(updated) => {
                setItem(updated);
                setIsEditing(false);
              }}
              onCancel={() => setIsEditing(false)}
            />
          ) : (
            <WishlistItemSummary item={item} onEdit={() => setIsEditing(true)} />
          )}

          <TagManager item={item} onUpdated={setItem} />

          <StatusChangeControl item={item} onUpdated={setItem} />
        </Stack>
      )}
    </AsyncState>
  );
}
