import { apiClient } from '@/lib/api-client';
import { wishlistItemSchema, type WishlistItem } from '../types/wishlist-item.schema';

export function assignTag(itemId: number, name: string): Promise<WishlistItem> {
  return apiClient
    .post(`/api/wishlist/${itemId}/tags`, { name })
    .then((response) => wishlistItemSchema.parse(response.data));
}
