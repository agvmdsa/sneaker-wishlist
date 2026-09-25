import { SneakerTile } from './sneaker-tile';
import type { CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';

interface SneakerScrollListProps {
  sneakers: CatalogSneaker[];
}

export function SneakerScrollList({ sneakers }: SneakerScrollListProps) {
  return (
    <div className="flex gap-4 overflow-x-auto pb-2">
      {sneakers.map((sneaker) => (
        <SneakerTile key={sneaker.id} sneaker={sneaker} />
      ))}
    </div>
  );
}
