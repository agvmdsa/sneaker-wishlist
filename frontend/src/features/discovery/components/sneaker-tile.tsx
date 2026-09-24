import { useState } from 'react';
import { Badge } from '@/components/badge';
import { Button } from '@/components/button';
import { Card } from '@/components/card';
import { Stack } from '@/components/stack';
import { formatCurrency } from '@/lib/format-currency';
import { AddToWishlistForm } from '@/features/catalog/components/add-to-wishlist-form';
import type { CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';

interface SneakerTileProps {
  sneaker: CatalogSneaker;
}

export function SneakerTile({ sneaker }: SneakerTileProps) {
  const [isAdding, setIsAdding] = useState(false);
  const [isAdded, setIsAdded] = useState(false);

  return (
    <Card className="w-48 shrink-0">
      <Stack gap="sm">
        {sneaker.imageUrl && (
          <img src={sneaker.imageUrl} alt={sneaker.title} className="w-full h-32 object-contain" />
        )}
        <p className="font-medium text-sm">{sneaker.title}</p>
        <p className="text-sm text-text-muted">{sneaker.brand}</p>
        {sneaker.avgPrice !== null && <Badge tone="info">{formatCurrency(sneaker.avgPrice)}</Badge>}

        {isAdded ? (
          <Badge tone="success">Already added</Badge>
        ) : (
          <Button variant="secondary" onClick={() => setIsAdding((current) => !current)}>
            {isAdding ? 'Cancel' : 'Add'}
          </Button>
        )}

        {isAdding && !isAdded && (
          <AddToWishlistForm
            sneaker={sneaker}
            onAdded={() => {
              setIsAdding(false);
              setIsAdded(true);
            }}
          />
        )}
      </Stack>
    </Card>
  );
}
