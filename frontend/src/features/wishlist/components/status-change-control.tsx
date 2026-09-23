import { useState, type FormEvent } from 'react';
import { Card } from '@/components/card';
import { Select } from '@/components/select';
import { Input } from '@/components/input';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import type { ApiError } from '@/types/api-error';
import { updateWishlistStatus } from '../api/update-wishlist-status';
import { statusUpdateInputSchema } from '../types/wishlist-item-input.schema';
import type { WishlistItem, WishlistStatus } from '../types/wishlist-item.schema';
import { getValidTransitions } from '../utils/status-transitions';

interface StatusChangeControlProps {
  item: WishlistItem;
  onUpdated: (updated: WishlistItem) => void;
}

export function StatusChangeControl({ item, onUpdated }: StatusChangeControlProps) {
  const validTargets = getValidTransitions(item.status);
  const [target, setTarget] = useState<WishlistStatus | ''>('');
  const [pricePaid, setPricePaid] = useState('');
  const [error, setError] = useState<string | undefined>(undefined);
  const [isSubmitting, setIsSubmitting] = useState(false);

  if (validTargets.length === 0) {
    return null;
  }

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (!target) {
      setError('Select a status');
      return;
    }

    if (target === 'OWNED' && pricePaid.trim() === '') {
      setError('Price paid is required when marking as Owned');
      return;
    }

    const result = statusUpdateInputSchema.safeParse({
      status: target,
      pricePaid: target === 'OWNED' ? Number(pricePaid) : undefined,
    });

    if (!result.success) {
      setError(result.error.issues[0]?.message ?? 'Invalid input');
      return;
    }

    setError(undefined);
    setIsSubmitting(true);

    updateWishlistStatus(item.id, result.data)
      .then((updated) => {
        onUpdated(updated);
        setTarget('');
        setPricePaid('');
      })
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setIsSubmitting(false));
  }

  return (
    <Card>
      <form onSubmit={handleSubmit}>
        <Stack gap="sm">
          <Select
            label="Change status to"
            value={target}
            onChange={(event) => setTarget(event.target.value as WishlistStatus | '')}
            error={target === '' ? error : undefined}
          >
            <option value="">Select...</option>
            {validTargets.map((status) => (
              <option key={status} value={status}>
                {status}
              </option>
            ))}
          </Select>

          {target === 'OWNED' && (
            <Input
              label="Price paid"
              type="number"
              step="0.01"
              value={pricePaid}
              onChange={(event) => setPricePaid(event.target.value)}
              error={target === 'OWNED' ? error : undefined}
            />
          )}

          <Button type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Updating...' : 'Update status'}
          </Button>
        </Stack>
      </form>
    </Card>
  );
}
