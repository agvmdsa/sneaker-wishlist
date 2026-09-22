import { useState } from 'react';
import { Badge } from '@/components/badge';
import { Button } from '@/components/button';
import { Stack } from '@/components/stack';
import { formatCurrency } from '@/lib/format-currency';
import type { CatalogSneaker } from '../types/catalog-sneaker.schema';
import { AddToWishlistForm } from './add-to-wishlist-form';

interface SneakerSearchResultCardProps {
  sneaker: CatalogSneaker;
  isInCollection: boolean;
  onAdded: (externalSneakerId: string) => void;
}

export function SneakerSearchResultCard({
  sneaker,
  isInCollection,
  onAdded,
}: SneakerSearchResultCardProps) {
  const [isAdding, setIsAdding] = useState(false);

  return (
    <div className="border border-border rounded p-4">
      <Stack direction="row" gap="md">
        {sneaker.imageUrl && (
          <img src={sneaker.imageUrl} alt={sneaker.title} className="w-16 h-16 object-contain" />
        )}
        <div className="flex-1">
          <p className="font-medium">{sneaker.title}</p>
          <p className="text-sm text-text-muted">{sneaker.brand}</p>
        </div>
        {sneaker.avgPrice !== null && <Badge tone="info">{formatCurrency(sneaker.avgPrice)}</Badge>}

        {isInCollection ? (
          <Badge tone="success">Already added</Badge>
        ) : (
          <Button variant="secondary" onClick={() => setIsAdding((current) => !current)}>
            {isAdding ? 'Cancel' : 'Add'}
          </Button>
        )}
      </Stack>

      {isAdding && !isInCollection && (
        <div className="mt-4">
          <AddToWishlistForm
            sneaker={sneaker}
            onAdded={() => {
              setIsAdding(false);
              onAdded(sneaker.id);
            }}
          />
        </div>
      )}
    </div>
  );
}
