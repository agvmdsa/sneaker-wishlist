import { useEffect, useState } from 'react';
import { checkAlreadyInCollection } from '@/features/wishlist/api/check-already-in-collection';

export function useAlreadyInCollection(externalSneakerIds: string[]) {
  const [ids, setIds] = useState<Set<string>>(new Set());
  const key = externalSneakerIds.join(',');

  useEffect(() => {
    let isActive = true;

    checkAlreadyInCollection(externalSneakerIds).then((result) => {
      if (isActive) setIds(new Set(result));
    });

    return () => {
      isActive = false;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [key]);

  return ids;
}
