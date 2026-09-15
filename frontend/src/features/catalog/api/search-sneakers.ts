import { apiClient } from '@/lib/api-client';
import { catalogSneakerSchema, type CatalogSneaker } from '../types/catalog-sneaker.schema';
import { z } from 'zod';

const searchResultsSchema = z.array(catalogSneakerSchema);

export function searchSneakers(query: string): Promise<CatalogSneaker[]> {
  return apiClient
    .get('/api/sneakers/search', { params: { query } })
    .then((response) => searchResultsSchema.parse(response.data));
}
