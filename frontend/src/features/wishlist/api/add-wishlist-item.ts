import { apiClient } from '@/lib/api-client';
import { wishlistItemSchema, type WishlistItem } from '../types/wishlist-item.schema';
import type { WishlistItemCreateInput } from '../types/wishlist-item-input.schema';

export function addWishlistItem(input: WishlistItemCreateInput): Promise<WishlistItem> {
  return apiClient
    .post('/api/wishlist', input)
    .then((response) => wishlistItemSchema.parse(response.data));
}
