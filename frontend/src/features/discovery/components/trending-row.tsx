import { useTrendingSneakers } from '../hooks/use-trending-sneakers';
import { SneakerRow } from './sneaker-row';

export function TrendingRow() {
  const { data, isLoading, error } = useTrendingSneakers();

  return (
    <SneakerRow
      title="Trending this week"
      sneakers={data}
      isLoading={isLoading}
      error={error?.message}
      emptyMessage="No trending sneakers to show right now."
    />
  );
}
