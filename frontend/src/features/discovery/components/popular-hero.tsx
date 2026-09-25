import { usePopularSneakers } from '../hooks/use-popular-sneakers';
import { SneakerRow } from './sneaker-row';

export function PopularHero() {
  const { data, isLoading, error } = usePopularSneakers();

  return (
    <SneakerRow
      title="Popular right now"
      sneakers={data}
      isLoading={isLoading}
      error={error?.message}
      emptyMessage="No popular sneakers to show right now."
    />
  );
}
