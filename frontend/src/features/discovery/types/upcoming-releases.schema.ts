import { z } from 'zod';
import { catalogSneakerSchema } from '@/features/catalog/types/catalog-sneaker.schema';

export const upcomingReleasesSchema = z.object({
  releasingSoon: z.array(catalogSneakerSchema),
  releasingThisMonth: z.array(catalogSneakerSchema),
  releasingLater: z.array(catalogSneakerSchema),
});

export type UpcomingReleases = z.infer<typeof upcomingReleasesSchema>;
