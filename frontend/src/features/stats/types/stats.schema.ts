import { z } from 'zod';

export const wishlistStatsSchema = z.object({
  totalActiveItems: z.number(),
  totalSpent: z.number(),
  estimatedWishlistValue: z.number(),
  mostFrequentBrand: z.string().nullable(),
});
export type WishlistStats = z.infer<typeof wishlistStatsSchema>;
