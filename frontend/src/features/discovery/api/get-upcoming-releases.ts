import { apiClient } from '@/lib/api-client';
import { upcomingReleasesSchema, type UpcomingReleases } from '../types/upcoming-releases.schema';

export function getUpcomingReleases(): Promise<UpcomingReleases> {
  return apiClient.get('/api/sneakers/upcoming-releases').then((response) => upcomingReleasesSchema.parse(response.data));
}
