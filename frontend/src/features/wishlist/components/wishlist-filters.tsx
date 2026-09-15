import { Select } from '@/components/select';
import { Input } from '@/components/input';
import { Stack } from '@/components/stack';
import type { WishlistStatus } from '../types/wishlist-item.schema';

interface WishlistFiltersProps {
  status: WishlistStatus | '';
  tag: string;
  onStatusChange: (status: WishlistStatus | '') => void;
  onTagChange: (tag: string) => void;
}

const STATUS_OPTIONS: Array<{ value: WishlistStatus | ''; label: string }> = [
  { value: '', label: 'All (Want & Owned)' },
  { value: 'WANT', label: 'Want' },
  { value: 'OWNED', label: 'Owned' },
  { value: 'SOLD', label: 'Sold' },
  { value: 'DONATED', label: 'Donated' },
];

export function WishlistFilters({
  status,
  tag,
  onStatusChange,
  onTagChange,
}: WishlistFiltersProps) {
  return (
    <Stack direction="row" gap="md">
      <Select
        label="Status"
        value={status}
        onChange={(event) => onStatusChange(event.target.value as WishlistStatus | '')}
      >
        {STATUS_OPTIONS.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </Select>
      <Input
        label="Tag"
        placeholder="e.g. grail"
        value={tag}
        onChange={(event) => onTagChange(event.target.value)}
      />
    </Stack>
  );
}
