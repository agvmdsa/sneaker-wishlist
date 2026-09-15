/** Mirrors the backend's ErrorResponseDto - the one shape every failed request resolves to. */
export interface ApiError {
  timestamp: string;
  status: number;
  message: string;
  fieldErrors: string[];
}
