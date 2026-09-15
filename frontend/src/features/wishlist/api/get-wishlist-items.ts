import { apiClient } from '@/lib/api-client';
import {
  wishlistItemPageSchema,
  type WishlistItemPage,
  type WishlistStatus,
} from '../types/wishlist-item.schema';

export interface GetWishlistItemsParams {
  status?: WishlistStatus;
  tag?: string;
  page?: number;
  size?: number;
}

export function getWishlistItems(params: GetWishlistItemsParams): Promise<WishlistItemPage> {
  return apiClient
    .get('/api/wishlist', { params })
    .then((response) => wishlistItemPageSchema.parse(response.data));
}
