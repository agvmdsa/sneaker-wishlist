import { useState } from 'react';
import { AsyncState } from '@/components/async-state';
import { EmptyState } from '@/components/empty-state';
import { Stack } from '@/components/stack';
import { Input } from '@/components/input';
import { useSneakerSearch, MIN_QUERY_LENGTH } from '../hooks/use-sneaker-search';
import { useAlreadyInCollection } from '../hooks/use-already-in-collection';
import { SneakerSearchResultCard } from './sneaker-search-result-card';

export function SneakerSearch() {
  const [query, setQuery] = useState('');
  const { results, isLoading, error } = useSneakerSearch(query);
  const alreadyInCollectionIds = useAlreadyInCollection(results.map((sneaker) => sneaker.id));
  const [justAddedIds, setJustAddedIds] = useState<Set<string>>(new Set());

  function handleAdded(externalSneakerId: string) {
    setJustAddedIds((current) => new Set(current).add(externalSneakerId));
  }

  return (
    <Stack gap="md">
      <Input
        label="Search sneakers"
        placeholder="e.g. Jordan 4"
        value={query}
        onChange={(event) => setQuery(event.target.value)}
      />

      {query.trim().length < MIN_QUERY_LENGTH ? (
        <EmptyState title={`Type at least ${MIN_QUERY_LENGTH} characters to search.`} />
      ) : (
        <AsyncState
          isLoading={isLoading}
          error={error?.message}
          isEmpty={results.length === 0}
          emptyMessage="No sneakers found."
        >
          <Stack gap="sm">
            {results.map((sneaker) => (
              <SneakerSearchResultCard
                key={sneaker.id}
                sneaker={sneaker}
                isInCollection={
                  alreadyInCollectionIds.has(sneaker.id) || justAddedIds.has(sneaker.id)
                }
                onAdded={handleAdded}
              />
            ))}
          </Stack>
        </AsyncState>
      )}
    </Stack>
  );
}
