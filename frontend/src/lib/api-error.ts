import type { AxiosError } from 'axios';
import type { ApiError } from '@/types/api-error';

export function isApiErrorShape(data: unknown): data is ApiError {
  return (
    typeof data === 'object' &&
    data !== null &&
    'status' in data &&
    'message' in data
  );
}

export function toApiError(error: AxiosError): ApiError {
  if (isApiErrorShape(error.response?.data)) {
    return error.response.data;
  }

  return {
    timestamp: new Date().toISOString(),
    status: error.response?.status ?? 0,
    message: error.response
      ? 'Unexpected error from the server.'
      : 'Could not reach the server. Check your connection and try again.',
    fieldErrors: [],
  };
}
