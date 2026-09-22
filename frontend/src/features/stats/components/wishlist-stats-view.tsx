import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { formatCurrency } from '@/lib/format-currency';
import { useWishlistStats } from '../hooks/use-wishlist-stats';

function StatRow({ label, value }: { label: string; value: string }) {
  return (
    <Stack direction="row" gap="md">
      <span className="text-sm text-text-muted flex-1">{label}</span>
      <span className="font-medium">{value}</span>
    </Stack>
  );
}

export function WishlistStatsView() {
  const { data, isLoading, error } = useWishlistStats();

  return (
    <AsyncState isLoading={isLoading} error={error?.message}>
      {data && (
        <Stack gap="sm">
          <StatRow label="Active items" value={String(data.totalActiveItems)} />
          <StatRow label="Total spent" value={formatCurrency(data.totalSpent)} />
          <StatRow
            label="Estimated wishlist value"
            value={formatCurrency(data.estimatedWishlistValue)}
          />
          <StatRow label="Most frequent brand" value={data.mostFrequentBrand ?? '—'} />
        </Stack>
      )}
    </AsyncState>
  );
}
