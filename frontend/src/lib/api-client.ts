import axios, { type AxiosError } from 'axios';
import { API_BASE_URL } from '@/config/env';
import { toApiError } from './api-error';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
});

apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => Promise.reject(toApiError(error)),
);
