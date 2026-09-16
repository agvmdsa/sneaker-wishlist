import { useState, type FormEvent } from 'react';
import { Badge } from '@/components/badge';
import { Input } from '@/components/input';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import type { ApiError } from '@/types/api-error';
import { assignTag } from '../api/assign-tag';
import { removeTag } from '../api/remove-tag';
import type { WishlistItem } from '../types/wishlist-item.schema';

interface TagManagerProps {
  item: WishlistItem;
  onUpdated: (updated: WishlistItem) => void;
}

export function TagManager({ item, onUpdated }: TagManagerProps) {
  const [newTag, setNewTag] = useState('');
  const [error, setError] = useState<string | undefined>(undefined);
  const [isAdding, setIsAdding] = useState(false);
  const [removingTagId, setRemovingTagId] = useState<number | null>(null);

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

  function handleRemove(tagId: number) {
    setRemovingTagId(tagId);
    setError(undefined);

    removeTag(item.id, tagId)
      .then(() => {
        onUpdated({ ...item, tags: item.tags.filter((tag) => tag.id !== tagId) });
      })
      .catch((apiError: ApiError) => setError(apiError.message))
      .finally(() => setRemovingTagId(null));
  }

  return (
    <Stack gap="sm">
      <Stack direction="row" gap="sm">
        {item.tags.map(
          (tag) =>
            tag.id !== null && (
              <Badge key={tag.id} tone="neutral">
                {tag.name}{' '}
                <button
                  type="button"
                  onClick={() => handleRemove(tag.id!)}
                  disabled={removingTagId === tag.id}
                  className="ml-1 text-xs cursor-pointer"
                  aria-label={`Remove tag ${tag.name}`}
                >
                  ×
                </button>
              </Badge>
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
