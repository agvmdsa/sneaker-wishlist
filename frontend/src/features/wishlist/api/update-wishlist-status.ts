import { apiClient } from '@/lib/api-client';
import { wishlistItemSchema, type WishlistItem } from '../types/wishlist-item.schema';
import type { StatusUpdateInput } from '../types/wishlist-item-input.schema';

export function updateWishlistStatus(id: number, input: StatusUpdateInput): Promise<WishlistItem> {
  return apiClient
    .patch(`/api/wishlist/${id}/status`, input)
    .then((response) => wishlistItemSchema.parse(response.data));
}
