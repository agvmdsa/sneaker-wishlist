import { describe, expect, it, vi, beforeEach } from 'vitest';
import { renderHook, waitFor, act } from '@testing-library/react';
import { useWishlistItem } from './use-wishlist-item';
import { getWishlistItem } from '../api/get-wishlist-item';
import type { WishlistItem } from '../types/wishlist-item.schema';
import type { ApiError } from '@/types/api-error';

vi.mock('../api/get-wishlist-item');

const mockedGetWishlistItem = vi.mocked(getWishlistItem);

const item: WishlistItem = {
  id: 1,
  externalSneakerId: 'ext-1',
  name: 'Jordan 4 Retro Rare Air',
  brand: 'Jordan',
  imageUrl: null,
  retailPrice: 220,
  size: '10',
  status: 'WANT',
  pricePaid: null,
  notes: null,
  priceDropDetected: false,
  addedAt: '2026-01-01T00:00:00',
  tags: [],
};

beforeEach(() => {
  mockedGetWishlistItem.mockReset();
});

describe('useWishlistItem', () => {
  it('starts in a loading state', () => {
    mockedGetWishlistItem.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useWishlistItem(1));

    expect(result.current.isLoading).toBe(true);
    expect(result.current.data).toBeNull();
  });

  it('resolves with the fetched item', async () => {
    mockedGetWishlistItem.mockResolvedValue(item);

    const { result } = renderHook(() => useWishlistItem(1));

    await waitFor(() => expect(result.current.isLoading).toBe(false));
    expect(result.current.data).toEqual(item);
    expect(result.current.error).toBeNull();
  });

  it('exposes an error when the fetch fails', async () => {
    const apiError: ApiError = {
      timestamp: '2026-01-01T00:00:00',
      status: 404,
      message: 'Not found',
      fieldErrors: [],
    };
    mockedGetWishlistItem.mockRejectedValue(apiError);

    const { result } = renderHook(() => useWishlistItem(1));

    await waitFor(() => expect(result.current.isLoading).toBe(false));
    expect(result.current.error).toEqual(apiError);
    expect(result.current.data).toBeNull();
  });

  it('setItem updates the data without refetching', async () => {
    mockedGetWishlistItem.mockResolvedValue(item);

    const { result } = renderHook(() => useWishlistItem(1));
    await waitFor(() => expect(result.current.isLoading).toBe(false));

    const updated = { ...item, notes: 'Updated' };
    act(() => {
      result.current.setItem(updated);
    });

    expect(result.current.data).toEqual(updated);
    expect(mockedGetWishlistItem).toHaveBeenCalledOnce();
  });
});
