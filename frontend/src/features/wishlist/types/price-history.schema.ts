import { z } from 'zod';

export const priceHistoryEntrySchema = z.object({
  price: z.number(),
  checkedAt: z.string(),
});
export type PriceHistoryEntry = z.infer<typeof priceHistoryEntrySchema>;
