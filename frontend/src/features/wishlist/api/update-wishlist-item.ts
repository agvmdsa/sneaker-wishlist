import { apiClient } from '@/lib/api-client';
import { wishlistItemSchema, type WishlistItem } from '../types/wishlist-item.schema';
import type { WishlistItemUpdateInput } from '../types/wishlist-item-input.schema';

export function updateWishlistItem(
  id: number,
  input: WishlistItemUpdateInput,
): Promise<WishlistItem> {
  return apiClient
    .patch(`/api/wishlist/${id}`, input)
    .then((response) => wishlistItemSchema.parse(response.data));
}
