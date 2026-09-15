import { useEffect, useReducer } from 'react';
import type { ApiError } from '@/types/api-error';
import { getWishlistItems, type GetWishlistItemsParams } from '../api/get-wishlist-items';
import type { WishlistItemPage } from '../types/wishlist-item.schema';

interface State {
  data: WishlistItemPage | null;
  isLoading: boolean;
  error: ApiError | null;
}

type Action =
  | { type: 'fetch-start' }
  | { type: 'fetch-success'; payload: WishlistItemPage }
  | { type: 'fetch-error'; payload: ApiError };

function reducer(state: State, action: Action): State {
  switch (action.type) {
    case 'fetch-start':
      return { ...state, isLoading: true, error: null };
    case 'fetch-success':
      return { data: action.payload, isLoading: false, error: null };
    case 'fetch-error':
      return { ...state, isLoading: false, error: action.payload };
  }
}

const initialState: State = { data: null, isLoading: true, error: null };

export function useWishlistItems(params: GetWishlistItemsParams) {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    let isActive = true;
    dispatch({ type: 'fetch-start' });

    getWishlistItems(params)
      .then((page) => {
        if (isActive) dispatch({ type: 'fetch-success', payload: page });
      })
      .catch((error: ApiError) => {
        if (isActive) dispatch({ type: 'fetch-error', payload: error });
      });

    return () => {
      isActive = false;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [params.status, params.tag, params.page, params.size]);

  return state;
}
