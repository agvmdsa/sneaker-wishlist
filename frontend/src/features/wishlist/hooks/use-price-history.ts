import { useEffect, useReducer } from 'react';
import type { ApiError } from '@/types/api-error';
import { getPriceHistory } from '../api/get-price-history';
import type { PriceHistoryEntry } from '../types/price-history.schema';

interface State {
  data: PriceHistoryEntry[];
  isLoading: boolean;
  error: ApiError | null;
}

type Action =
  | { type: 'fetch-start' }
  | { type: 'fetch-success'; payload: PriceHistoryEntry[] }
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

const initialState: State = { data: [], isLoading: true, error: null };

export function usePriceHistory(itemId: number) {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    let isActive = true;
    dispatch({ type: 'fetch-start' });

    getPriceHistory(itemId)
      .then((result) => {
        if (isActive) dispatch({ type: 'fetch-success', payload: result });
      })
      .catch((error: ApiError) => {
        if (isActive) dispatch({ type: 'fetch-error', payload: error });
      });

    return () => {
      isActive = false;
    };
  }, [itemId]);

  return state;
}
