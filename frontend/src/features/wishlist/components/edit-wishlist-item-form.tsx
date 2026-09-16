import { useState, type FormEvent } from 'react';
import { Input } from '@/components/input';
import { TextArea } from '@/components/text-area';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import type { ApiError } from '@/types/api-error';
import { updateWishlistItem } from '../api/update-wishlist-item';
import { wishlistItemUpdateInputSchema } from '../types/wishlist-item-input.schema';
import type { WishlistItem } from '../types/wishlist-item.schema';

interface EditWishlistItemFormProps {
  item: WishlistItem;
  onSaved: (updated: WishlistItem) => void;
  onCancel: () => void;
}

export function EditWishlistItemForm({ item, onSaved, onCancel }: EditWishlistItemFormProps) {
  const [size, setSize] = useState(item.size);
  const [notes, setNotes] = useState(item.notes ?? '');
  const [error, setError] = useState<string | undefined>(undefined);
  const [isSubmitting, setIsSubmitting] = useState(false);

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const result = wishlistItemUpdateInputSchema.safeParse({ size, notes });

    if (!result.success) {
      setError(result.error.issues[0]?.message ?? 'Invalid input');
      return;
    }

    setError(undefined);
    setIsSubmitting(true);

    updateWishlistItem(item.id, result.data)
      .then((updated) => onSaved(updated))
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setIsSubmitting(false));
  }

  return (
    <form onSubmit={handleSubmit}>
      <Stack gap="sm">
        <Input
          label="Size"
          value={size}
          onChange={(event) => setSize(event.target.value)}
          error={error}
        />
        <TextArea label="Notes" value={notes} onChange={(event) => setNotes(event.target.value)} />
        <Stack direction="row" gap="sm">
          <Button type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Saving...' : 'Save'}
          </Button>
          <Button type="button" variant="secondary" onClick={onCancel}>
            Cancel
          </Button>
        </Stack>
      </Stack>
    </form>
  );
}
