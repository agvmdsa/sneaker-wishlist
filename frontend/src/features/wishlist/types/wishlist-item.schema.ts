import { z } from 'zod';

export const wishlistStatusSchema = z.enum(['WANT', 'OWNED', 'SOLD', 'DONATED']);
export type WishlistStatus = z.infer<typeof wishlistStatusSchema>;

export const tagSchema = z.object({
  id: z.number().nullable(),
  name: z.string(),
});
export type Tag = z.infer<typeof tagSchema>;

export const wishlistItemSchema = z.object({
  id: z.number(),
  externalSneakerId: z.string(),
  name: z.string(),
  brand: z.string(),
  imageUrl: z.string().nullable(),
  retailPrice: z.number().nullable(),
  size: z.string(),
  status: wishlistStatusSchema,
  pricePaid: z.number().nullable(),
  notes: z.string().nullable(),
  priceDropDetected: z.boolean(),
  addedAt: z.string(),
  tags: z.array(tagSchema),
});
export type WishlistItem = z.infer<typeof wishlistItemSchema>;

export const wishlistItemPageSchema = z.object({
  content: z.array(wishlistItemSchema),
  totalElements: z.number(),
  totalPages: z.number(),
  number: z.number(),
  size: z.number(),
});
export type WishlistItemPage = z.infer<typeof wishlistItemPageSchema>;
