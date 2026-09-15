import { Badge } from '@/components/badge';
import type { CatalogSneaker } from '../types/catalog-sneaker.schema';

interface SneakerSearchResultCardProps {
  sneaker: CatalogSneaker;
}

export function SneakerSearchResultCard({ sneaker }: SneakerSearchResultCardProps) {
  return (
    <div className="flex items-center gap-4 border border-border rounded p-4">
      {sneaker.imageUrl && (
        <img src={sneaker.imageUrl} alt={sneaker.title} className="w-16 h-16 object-contain" />
      )}
      <div className="flex-1">
        <p className="font-medium">{sneaker.title}</p>
        <p className="text-sm text-text-muted">{sneaker.brand}</p>
      </div>
      {sneaker.avgPrice !== null && <Badge tone="info">${sneaker.avgPrice}</Badge>}
    </div>
  );
}
