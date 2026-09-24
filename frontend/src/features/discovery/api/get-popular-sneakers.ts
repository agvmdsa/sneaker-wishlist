import { apiClient } from '@/lib/api-client';
import { catalogSneakerSchema, type CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';
import { z } from 'zod';

const popularSneakersSchema = z.array(catalogSneakerSchema);

export function getPopularSneakers(): Promise<CatalogSneaker[]> {
  return apiClient.get('/api/sneakers/popular').then((response) => popularSneakersSchema.parse(response.data));
}
