import { z } from 'zod';
import { apiClient } from '@/lib/api-client';
import { priceHistoryEntrySchema, type PriceHistoryEntry } from '../types/price-history.schema';

const priceHistorySchema = z.array(priceHistoryEntrySchema);

export function getPriceHistory(itemId: number): Promise<PriceHistoryEntry[]> {
  return apiClient
    .get(`/api/wishlist/${itemId}/price-history`)
    .then((response) => priceHistorySchema.parse(response.data));
}
