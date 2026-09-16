import { apiClient } from '@/lib/api-client';

export function removeTag(itemId: number, tagId: number): Promise<void> {
  return apiClient.delete(`/api/wishlist/${itemId}/tags/${tagId}`).then(() => undefined);
}
