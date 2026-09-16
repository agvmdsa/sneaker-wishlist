import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import type { ApiError } from '@/types/api-error';
import { deleteWishlistItem } from '../api/delete-wishlist-item';

interface RemoveItemActionProps {
  itemId: number;
}

export function RemoveItemAction({ itemId }: RemoveItemActionProps) {
  const navigate = useNavigate();
  const [isConfirming, setIsConfirming] = useState(false);
  const [isDeleting, setIsDeleting] = useState(false);
  const [error, setError] = useState<string | undefined>(undefined);

  function handleConfirm() {
    setIsDeleting(true);
    setError(undefined);

    deleteWishlistItem(itemId)
      .then(() => navigate('/'))
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setIsDeleting(false));
  }

  if (!isConfirming) {
    return (
      <Button variant="danger" onClick={() => setIsConfirming(true)}>
        Remove
      </Button>
    );
  }

  return (
    <Stack direction="row" gap="sm">
      <span className="text-sm text-text-muted">Remove this item?</span>
      <Button variant="danger" onClick={handleConfirm} disabled={isDeleting}>
        {isDeleting ? 'Removing...' : 'Confirm'}
      </Button>
      <Button
        type="button"
        variant="secondary"
        onClick={() => setIsConfirming(false)}
        disabled={isDeleting}
      >
        Cancel
      </Button>
      {error && <span className="text-sm text-danger">{error}</span>}
    </Stack>
  );
}
