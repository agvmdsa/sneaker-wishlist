import type { WishlistStatus } from '../types/wishlist-item.schema';

const toneByStatus: Record<WishlistStatus, 'neutral' | 'info' | 'success' | 'danger'> = {
  WANT: 'info',
  OWNED: 'success',
  SOLD: 'neutral',
  DONATED: 'neutral',
};

export function statusTone(status: WishlistStatus) {
  return toneByStatus[status];
}
