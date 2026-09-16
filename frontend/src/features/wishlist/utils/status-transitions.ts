import type { WishlistStatus } from '../types/wishlist-item.schema';

const transitions: Record<WishlistStatus, WishlistStatus[]> = {
  WANT: ['OWNED'],
  OWNED: ['SOLD', 'DONATED'],
  SOLD: [],
  DONATED: [],
};

export function getValidTransitions(status: WishlistStatus): WishlistStatus[] {
  return transitions[status];
}
