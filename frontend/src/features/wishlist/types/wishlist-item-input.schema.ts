import { z } from 'zod';
import { wishlistStatusSchema } from './wishlist-item.schema';

export const wishlistItemCreateInputSchema = z.object({
  externalSneakerId: z.string().min(1),
  name: z.string().min(1),
  brand: z.string().min(1),
  imageUrl: z.string().optional(),
  retailPrice: z.number().nonnegative().optional(),
  size: z.string().min(1),
});
export type WishlistItemCreateInput = z.infer<typeof wishlistItemCreateInputSchema>;

export const wishlistItemUpdateInputSchema = z.object({
  size: z.string().min(1).optional(),
  notes: z.string().optional(),
});
export type WishlistItemUpdateInput = z.infer<typeof wishlistItemUpdateInputSchema>;

export const statusUpdateInputSchema = z.object({
  status: wishlistStatusSchema,
  pricePaid: z.number().nonnegative().optional(),
});
export type StatusUpdateInput = z.infer<typeof statusUpdateInputSchema>;
