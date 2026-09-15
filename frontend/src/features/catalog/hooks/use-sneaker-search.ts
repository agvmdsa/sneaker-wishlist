import { useEffect, useReducer } from 'react';
import AwesomeDebouncePromise from 'awesome-debounce-promise';
import type { ApiError } from '@/types/api-error';
import { searchSneakers } from '../api/search-sneakers';
import type { CatalogSneaker } from '../types/catalog-sneaker.schema';

export const MIN_QUERY_LENGTH = 2;

const debouncedSearchSneakers = AwesomeDebouncePromise(searchSneakers, 500);

interface State {
  results: CatalogSneaker[];
  isLoading: boolean;
  error: ApiError | null;
}

type Action =
  | { type: 'reset' }
  | { type: 'fetch-start' }
  | { type: 'fetch-success'; payload: CatalogSneaker[] }
  | { type: 'fetch-error'; payload: ApiError };

function reducer(state: State, action: Action): State {
  switch (action.type) {
    case 'reset':
      return { results: [], isLoading: false, error: null };
    case 'fetch-start':
      return { ...state, isLoading: true, error: null };
    case 'fetch-success':
      return { results: action.payload, isLoading: false, error: null };
    case 'fetch-error':
      return { ...state, isLoading: false, error: action.payload };
  }
}

const initialState: State = { results: [], isLoading: false, error: null };

export function useSneakerSearch(query: string) {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    let isActive = true;
    const trimmedQuery = query.trim();

    if (trimmedQuery.length < MIN_QUERY_LENGTH) {
      dispatch({ type: 'reset' });
      return;
    }

    dispatch({ type: 'fetch-start' });

    debouncedSearchSneakers(trimmedQuery)
      .then((results) => {
        if (isActive && results) dispatch({ type: 'fetch-success', payload: results });
      })
      .catch((error: ApiError) => {
        if (isActive) dispatch({ type: 'fetch-error', payload: error });
      });

    return () => {
      isActive = false;
    };
  }, [query]);

  return state;
}
