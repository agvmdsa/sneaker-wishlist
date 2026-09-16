import { useState } from 'react';
import { AsyncState } from '@/components/async-state';
import { useWishlistItem } from '../hooks/use-wishlist-item';
import { WishlistItemSummary } from './wishlist-item-summary';
import { EditWishlistItemForm } from './edit-wishlist-item-form';

interface WishlistItemDetailProps {
  id: number;
}

export function WishlistItemDetail({ id }: WishlistItemDetailProps) {
  const { data: item, isLoading, error, setItem } = useWishlistItem(id);
  const [isEditing, setIsEditing] = useState(false);

  return (
    <AsyncState isLoading={isLoading} error={error?.message}>
      {item &&
        (isEditing ? (
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
        ))}
    </AsyncState>
  );
}
