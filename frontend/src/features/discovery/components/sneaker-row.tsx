import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { SneakerScrollList } from './sneaker-scroll-list';
import type { CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';

interface SneakerRowProps {
  title: string;
  sneakers: CatalogSneaker[];
  isLoading: boolean;
  error?: string;
  emptyMessage: string;
}

export function SneakerRow({ title, sneakers, isLoading, error, emptyMessage }: SneakerRowProps) {
  return (
    <Stack gap="sm">
      <h2 className="text-base font-semibold">{title}</h2>
      <AsyncState isLoading={isLoading} error={error} isEmpty={sneakers.length === 0} emptyMessage={emptyMessage}>
        <SneakerScrollList sneakers={sneakers} />
      </AsyncState>
    </Stack>
  );
}
