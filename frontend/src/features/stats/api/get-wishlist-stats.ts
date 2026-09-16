import { apiClient } from '@/lib/api-client';
import { wishlistStatsSchema, type WishlistStats } from '../types/stats.schema';

export function getWishlistStats(): Promise<WishlistStats> {
  return apiClient
    .get('/api/wishlist/stats')
    .then((response) => wishlistStatsSchema.parse(response.data));
}
