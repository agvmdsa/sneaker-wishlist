import { z } from 'zod';

export const catalogSneakerSchema = z.object({
  id: z.string(),
  title: z.string(),
  brand: z.string(),
  imageUrl: z.string().nullable(),
  avgPrice: z.number().nullable(),
  productType: z.string(),
});
export type CatalogSneaker = z.infer<typeof catalogSneakerSchema>;
