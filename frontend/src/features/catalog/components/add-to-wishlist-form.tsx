import { useState, type FormEvent } from 'react';
import { Input } from '@/components/input';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import type { ApiError } from '@/types/api-error';
import { addWishlistItem } from '@/features/wishlist/api/add-wishlist-item';
import { wishlistItemCreateInputSchema } from '@/features/wishlist/types/wishlist-item-input.schema';
import type { CatalogSneaker } from '../types/catalog-sneaker.schema';

interface AddToWishlistFormProps {
  sneaker: CatalogSneaker;
  onAdded: () => void;
}

export function AddToWishlistForm({ sneaker, onAdded }: AddToWishlistFormProps) {
  const [size, setSize] = useState('');
  const [error, setError] = useState<string | undefined>(undefined);
  const [isSubmitting, setIsSubmitting] = useState(false);

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const result = wishlistItemCreateInputSchema.safeParse({
      externalSneakerId: sneaker.id,
      name: sneaker.title,
      brand: sneaker.brand,
      imageUrl: sneaker.imageUrl ?? undefined,
      retailPrice: sneaker.avgPrice ?? undefined,
      size,
    });

    if (!result.success) {
      setError(result.error.issues[0]?.message ?? 'Invalid input');
      return;
    }

    setError(undefined);
    setIsSubmitting(true);

    addWishlistItem(result.data)
      .then(() => onAdded())
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setIsSubmitting(false));
  }

  return (
    <form onSubmit={handleSubmit}>
      <Stack direction="row" gap="sm">
        <Input
          label="Size"
          placeholder="e.g. 10"
          value={size}
          onChange={(event) => setSize(event.target.value)}
          error={error}
        />
        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Adding...' : 'Confirm'}
        </Button>
      </Stack>
    </form>
  );
}
