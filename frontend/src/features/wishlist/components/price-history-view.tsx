import { AsyncState } from '@/components/async-state';
import { Stack } from '@/components/stack';
import { usePriceHistory } from '../hooks/use-price-history';

interface PriceHistoryViewProps {
  itemId: number;
}

export function PriceHistoryView({ itemId }: PriceHistoryViewProps) {
  const { data, isLoading, error } = usePriceHistory(itemId);

  return (
    <Stack gap="sm">
      <p className="text-sm text-text-muted">Price history</p>
      <AsyncState
        isLoading={isLoading}
        error={error?.message}
        isEmpty={data.length === 0}
        emptyMessage="No price checks recorded yet."
      >
        <Stack gap="sm">
          {data.map((entry) => (
            <div
              key={entry.checkedAt}
              className="flex items-center justify-between border-b border-border pb-2"
            >
              <span className="text-sm text-text-muted">
                {new Date(entry.checkedAt).toLocaleString()}
              </span>
              <span className="font-medium">${entry.price}</span>
            </div>
          ))}
        </Stack>
      </AsyncState>
    </Stack>
  );
}
