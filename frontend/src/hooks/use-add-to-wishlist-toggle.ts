import { useState } from 'react';

export function useAddToWishlistToggle() {
  const [isAdding, setIsAdding] = useState(false);

  return {
    isAdding,
    toggleAdding: () => setIsAdding((current) => !current),
    closeAdding: () => setIsAdding(false),
  };
}
