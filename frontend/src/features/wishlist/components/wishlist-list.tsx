import { useState } from 'react';
import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { Button } from '@/components/button';
import { useWishlistItems } from '../hooks/use-wishlist-items';
import { WishlistItemCard } from './wishlist-item-card';
import { WishlistFilters } from './wishlist-filters';
import type { WishlistStatus } from '../types/wishlist-item.schema';

const PAGE_SIZE = 10;

export function WishlistList() {
  const [status, setStatus] = useState<WishlistStatus | ''>('');
  const [tagInput, setTagInput] = useState('');
  const [page, setPage] = useState(0);

  const { data, isLoading, error } = useWishlistItems({
    status: status || undefined,
    tag: tagInput || undefined,
    page,
    size: PAGE_SIZE,
  });

  function handleStatusChange(nextStatus: WishlistStatus | '') {
    setStatus(nextStatus);
    setPage(0);
  }

  function handleTagChange(nextTag: string) {
    setTagInput(nextTag);
    setPage(0);
  }

  return (
    <Stack gap="md">
      <WishlistFilters
        status={status}
        tag={tagInput}
        onStatusChange={handleStatusChange}
        onTagChange={handleTagChange}
      />

      <AsyncState
        isLoading={isLoading}
        error={error?.message}
        isEmpty={data?.content.length === 0}
        emptyMessage="No items match these filters."
      >
        <Stack gap="sm">
          {data?.content.map((item) => (
            <WishlistItemCard key={item.id} item={item} />
          ))}
        </Stack>
      </AsyncState>

      {data && data.page.totalPages > 1 && (
        <Stack direction="row" gap="sm">
          <Button
            variant="secondary"
            disabled={page === 0}
            onClick={() => setPage((current) => current - 1)}
          >
            Previous
          </Button>
          <Button
            variant="secondary"
            disabled={page + 1 >= data.page.totalPages}
            onClick={() => setPage((current) => current + 1)}
          >
            Next
          </Button>
        </Stack>
      )}
    </Stack>
  );
}
