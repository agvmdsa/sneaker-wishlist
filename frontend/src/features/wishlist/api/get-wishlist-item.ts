import { apiClient } from '@/lib/api-client';
import { wishlistItemSchema, type WishlistItem } from '../types/wishlist-item.schema';

export function getWishlistItem(id: number): Promise<WishlistItem> {
  return apiClient
    .get(`/api/wishlist/${id}`)
    .then((response) => wishlistItemSchema.parse(response.data));
}
