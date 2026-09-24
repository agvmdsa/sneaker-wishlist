import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { useTrendingSneakers } from '../hooks/use-trending-sneakers';
import { SneakerTile } from './sneaker-tile';

export function TrendingRow() {
  const { data, isLoading, error } = useTrendingSneakers();

  return (
    <Stack gap="sm">
      <h2 className="text-base font-semibold">Trending this week</h2>
      <AsyncState isLoading={isLoading} error={error?.message} isEmpty={data.length === 0} emptyMessage="No trending sneakers to show right now.">
        <div className="flex gap-4 overflow-x-auto pb-2">
          {data.map((sneaker) => (
            <SneakerTile key={sneaker.id} sneaker={sneaker} />
          ))}
        </div>
      </AsyncState>
    </Stack>
  );
}
