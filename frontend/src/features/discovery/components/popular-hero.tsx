import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { usePopularSneakers } from '../hooks/use-popular-sneakers';
import { SneakerTile } from './sneaker-tile';

export function PopularHero() {
  const { data, isLoading, error } = usePopularSneakers();

  return (
    <Stack gap="sm">
      <h2 className="text-base font-semibold">Popular right now</h2>
      <AsyncState isLoading={isLoading} error={error?.message} isEmpty={data.length === 0} emptyMessage="No popular sneakers to show right now.">
        <div className="flex gap-4 overflow-x-auto pb-2">
          {data.map((sneaker) => (
            <SneakerTile key={sneaker.id} sneaker={sneaker} />
          ))}
        </div>
      </AsyncState>
    </Stack>
  );
}
