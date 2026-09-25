import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { useUpcomingReleases } from '../hooks/use-upcoming-releases';
import { SneakerScrollList } from './sneaker-scroll-list';
import type { CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';

interface BucketRowProps {
  label: string;
  sneakers: CatalogSneaker[];
}

function BucketRow({ label, sneakers }: BucketRowProps) {
  if (sneakers.length === 0) {
    return null;
  }

  return (
    <Stack gap="sm">
      <h3 className="text-sm font-medium text-text-muted">{label}</h3>
      <SneakerScrollList sneakers={sneakers} />
    </Stack>
  );
}

export function UpcomingReleasesRow() {
  const { data, isLoading, error } = useUpcomingReleases();
  const isEmpty =
    data !== null &&
    data.releasingSoon.length === 0 &&
    data.releasingThisMonth.length === 0 &&
    data.releasingLater.length === 0;

  return (
    <Stack gap="md">
      <h2 className="text-base font-semibold">Upcoming releases</h2>
      <AsyncState isLoading={isLoading} error={error?.message} isEmpty={isEmpty} emptyMessage="No upcoming releases to show right now.">
        {data && (
          <Stack gap="md">
            <BucketRow label="Releasing soon" sneakers={data.releasingSoon} />
            <BucketRow label="Releasing this month" sneakers={data.releasingThisMonth} />
            <BucketRow label="Later" sneakers={data.releasingLater} />
          </Stack>
        )}
      </AsyncState>
    </Stack>
  );
}
