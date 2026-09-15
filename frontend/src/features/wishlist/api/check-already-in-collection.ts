import { z } from 'zod';
import { apiClient } from '@/lib/api-client';

const alreadyInCollectionSchema = z.array(z.string());

export function checkAlreadyInCollection(externalSneakerIds: string[]): Promise<string[]> {
  if (externalSneakerIds.length === 0) {
    return Promise.resolve([]);
  }

  return apiClient
    .post('/api/wishlist/check', externalSneakerIds)
    .then((response) => alreadyInCollectionSchema.parse(response.data));
}
