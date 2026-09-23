import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/button';
import { Dialog } from '@/components/dialog';
import { Stack } from '@/components/stack';
import { useDialog } from '@/hooks/use-dialog';
import type { ApiError } from '@/types/api-error';
import { deleteWishlistItem } from '../api/delete-wishlist-item';

interface RemoveItemActionProps {
  itemId: number;
}

function RemoveTrigger() {
  const { open } = useDialog();

  return (
    <Button variant="danger" onClick={open}>
      Remove
    </Button>
  );
}

interface RemoveConfirmationProps {
  itemId: number;
}

function RemoveConfirmation({ itemId }: RemoveConfirmationProps) {
  const navigate = useNavigate();
  const { close } = useDialog();
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

  return (
    <Stack gap="md">
      <p>Remove this item from your wishlist?</p>
      {error && <p className="text-sm text-danger">{error}</p>}
      <Stack direction="row" gap="sm">
        <Button variant="danger" onClick={handleConfirm} disabled={isDeleting}>
          {isDeleting ? 'Removing...' : 'Confirm'}
        </Button>
        <Button variant="secondary" onClick={close} disabled={isDeleting}>
          Cancel
        </Button>
      </Stack>
    </Stack>
  );
}

export function RemoveItemAction({ itemId }: RemoveItemActionProps) {
  return (
    <Dialog>
      <RemoveTrigger />
      <Dialog.Content>
        <RemoveConfirmation itemId={itemId} />
      </Dialog.Content>
    </Dialog>
  );
}
