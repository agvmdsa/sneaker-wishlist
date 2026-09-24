import { useEffect, useReducer } from 'react';
import type { ApiError } from '@/types/api-error';
import { getUpcomingReleases } from '../api/get-upcoming-releases';
import type { UpcomingReleases } from '../types/upcoming-releases.schema';

interface State {
  data: UpcomingReleases | null;
  isLoading: boolean;
  error: ApiError | null;
}

type Action =
  | { type: 'fetch-start' }
  | { type: 'fetch-success'; payload: UpcomingReleases }
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

const initialState: State = { data: null, isLoading: false, error: null };

export function useUpcomingReleases() {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    let isActive = true;
    dispatch({ type: 'fetch-start' });

    getUpcomingReleases()
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
