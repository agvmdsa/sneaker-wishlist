import { useEffect, useState } from 'react';
import { checkAlreadyInCollection } from '@/features/wishlist/api/check-already-in-collection';

export function useAlreadyInCollection(externalSneakerIds: string[]) {
  const [ids, setIds] = useState<Set<string>>(new Set());
  const key = externalSneakerIds.join(',');

  useEffect(() => {
    let isActive = true;
    const idsToCheck = key === '' ? [] : key.split(',');

    checkAlreadyInCollection(idsToCheck).then((result) => {
      if (isActive) setIds(new Set(result));
    });

    return () => {
      isActive = false;
    };
  }, [key]);

  return ids;
}
