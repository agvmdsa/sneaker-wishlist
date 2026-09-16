import { apiClient } from '@/lib/api-client';

export function deleteWishlistItem(id: number): Promise<void> {
  return apiClient.delete(`/api/wishlist/${id}`).then(() => undefined);
}
