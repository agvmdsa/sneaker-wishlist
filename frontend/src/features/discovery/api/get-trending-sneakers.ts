import { apiClient } from '@/lib/api-client';
import { catalogSneakerSchema, type CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';
import { z } from 'zod';

const trendingSneakersSchema = z.array(catalogSneakerSchema);

export function getTrendingSneakers(): Promise<CatalogSneaker[]> {
  return apiClient.get('/api/sneakers/trending').then((response) => trendingSneakersSchema.parse(response.data));
}
