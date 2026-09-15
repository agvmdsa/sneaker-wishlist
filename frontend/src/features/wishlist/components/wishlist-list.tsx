import { useState } from 'react';
import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { Button } from '@/components/button';
import { useWishlistItems } from '../hooks/use-wishlist-items';
import { WishlistItemCard } from './wishlist-item-card';

const PAGE_SIZE = 10;

export function WishlistList() {
  const [page, setPage] = useState(0);
  const { data, isLoading, error } = useWishlistItems({ page, size: PAGE_SIZE });

  return (
    <Stack gap="md">
      <AsyncState
        isLoading={isLoading}
        error={error?.message}
        isEmpty={data?.content.length === 0}
        emptyMessage="No items in your wishlist yet."
      >
        <Stack gap="sm">
          {data?.content.map((item) => <WishlistItemCard key={item.id} item={item} />)}
        </Stack>
      </AsyncState>

      {data && data.totalPages > 1 && (
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
            disabled={page + 1 >= data.totalPages}
            onClick={() => setPage((current) => current + 1)}
          >
            Next
          </Button>
        </Stack>
      )}
    </Stack>
  );
}
