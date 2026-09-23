import { useState, type FormEvent } from 'react';
import { Badge } from '@/components/badge';
import { Button } from '@/components/button';
import { Dialog } from '@/components/dialog';
import { Input } from '@/components/input';
import { Stack } from '@/components/stack';
import { useDialog } from '@/hooks/use-dialog';
import type { ApiError } from '@/types/api-error';
import { assignTag } from '../api/assign-tag';
import { removeTag } from '../api/remove-tag';
import type { WishlistItem } from '../types/wishlist-item.schema';

interface TagManagerProps {
  item: WishlistItem;
  onUpdated: (updated: WishlistItem) => void;
}

interface RemoveTagTriggerProps {
  tagName: string;
}

function RemoveTagTrigger({ tagName }: RemoveTagTriggerProps) {
  const { open } = useDialog();

  return (
    <button
      type="button"
      onClick={open}
      className="ml-1 text-xs cursor-pointer"
      aria-label={`Remove tag ${tagName}`}
    >
      ×
    </button>
  );
}

interface RemoveTagConfirmationProps {
  itemId: number;
  tagId: number;
  tagName: string;
  onRemoved: (tagId: number) => void;
}

function RemoveTagConfirmation({ itemId, tagId, tagName, onRemoved }: RemoveTagConfirmationProps) {
  const { close } = useDialog();
  const [isRemoving, setIsRemoving] = useState(false);
  const [error, setError] = useState<string | undefined>(undefined);

  function handleConfirm() {
    setIsRemoving(true);
    setError(undefined);

    removeTag(itemId, tagId)
      .then(() => {
        onRemoved(tagId);
        close();
      })
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setIsRemoving(false));
  }

  return (
    <Stack gap="md">
      <p>Remove tag "{tagName}"?</p>
      {error && <p className="text-sm text-danger">{error}</p>}
      <Stack direction="row" gap="sm">
        <Button variant="danger" onClick={handleConfirm} disabled={isRemoving}>
          {isRemoving ? 'Removing...' : 'Confirm'}
        </Button>
        <Button variant="secondary" onClick={close} disabled={isRemoving}>
          Cancel
        </Button>
      </Stack>
    </Stack>
  );
}

interface TagBadgeProps {
  itemId: number;
  tagId: number;
  tagName: string;
  onRemoved: (tagId: number) => void;
}

function TagBadge({ itemId, tagId, tagName, onRemoved }: TagBadgeProps) {
  return (
    <Dialog>
      <Badge tone="neutral">
        {tagName} <RemoveTagTrigger tagName={tagName} />
      </Badge>
      <Dialog.Content>
        <RemoveTagConfirmation itemId={itemId} tagId={tagId} tagName={tagName} onRemoved={onRemoved} />
      </Dialog.Content>
    </Dialog>
  );
}

export function TagManager({ item, onUpdated }: TagManagerProps) {
  const [newTag, setNewTag] = useState('');
  const [error, setError] = useState<string | undefined>(undefined);
  const [isAdding, setIsAdding] = useState(false);

  function handleAdd(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (newTag.trim() === '') {
      setError('Tag name is required');
      return;
    }

    setError(undefined);
    setIsAdding(true);

    assignTag(item.id, newTag.trim())
      .then((updated) => {
        onUpdated(updated);
        setNewTag('');
      })
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setIsAdding(false));
  }

  function handleTagRemoved(tagId: number) {
    onUpdated({ ...item, tags: item.tags.filter((tag) => tag.id !== tagId) });
  }

  return (
    <Stack gap="sm">
      <Stack direction="row" gap="sm">
        {item.tags.map(
          (tag) =>
            tag.id !== null && (
              <TagBadge
                key={tag.id}
                itemId={item.id}
                tagId={tag.id}
                tagName={tag.name}
                onRemoved={handleTagRemoved}
              />
            ),
        )}
      </Stack>

      <form onSubmit={handleAdd}>
        <Stack direction="row" gap="sm">
          <Input
            label="Add tag"
            value={newTag}
            onChange={(event) => setNewTag(event.target.value)}
            error={error}
          />
          <Button type="submit" disabled={isAdding}>
            {isAdding ? 'Adding...' : 'Add'}
          </Button>
        </Stack>
      </form>
    </Stack>
  );
}
