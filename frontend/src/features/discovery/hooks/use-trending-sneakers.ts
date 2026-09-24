import { useEffect, useReducer } from 'react';
import type { ApiError } from '@/types/api-error';
import { getTrendingSneakers } from '../api/get-trending-sneakers';
import type { CatalogSneaker } from '@/features/catalog/types/catalog-sneaker.schema';

interface State {
  data: CatalogSneaker[];
  isLoading: boolean;
  error: ApiError | null;
}

type Action =
  | { type: 'fetch-start' }
  | { type: 'fetch-success'; payload: CatalogSneaker[] }
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

const initialState: State = { data: [], isLoading: false, error: null };

export function useTrendingSneakers() {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    let isActive = true;
    dispatch({ type: 'fetch-start' });

    getTrendingSneakers()
      .then((data) => {
        if (isActive) dispatch({ type: 'fetch-success', payload: data });
      })
      .catch((error: ApiError) => {
        if (isActive) dispatch({ type: 'fetch-error', payload: error });
      });

    return () => {
      isActive = false;
    };
  }, []);

  return state;
}
