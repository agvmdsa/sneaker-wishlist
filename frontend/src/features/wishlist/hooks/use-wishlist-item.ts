import { useEffect, useReducer } from 'react';
import type { ApiError } from '@/types/api-error';
import { getWishlistItem } from '../api/get-wishlist-item';
import type { WishlistItem } from '../types/wishlist-item.schema';

interface State {
  data: WishlistItem | null;
  isLoading: boolean;
  error: ApiError | null;
}

type Action =
  | { type: 'fetch-start' }
  | { type: 'fetch-success'; payload: WishlistItem }
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

export function useWishlistItem(id: number) {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    let isActive = true;
    dispatch({ type: 'fetch-start' });

    getWishlistItem(id)
      .then((result) => {
        if (isActive) dispatch({ type: 'fetch-success', payload: result });
      })
      .catch((error: ApiError) => {
        if (isActive) dispatch({ type: 'fetch-error', payload: error });
      });

    return () => {
      isActive = false;
    };
  }, [id]);

  function setItem(item: WishlistItem) {
    dispatch({ type: 'fetch-success', payload: item });
  }

  return { ...state, setItem };
}
