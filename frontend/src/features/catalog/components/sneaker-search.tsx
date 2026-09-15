import { useState } from 'react';
import { AsyncState } from '@/components/async-state';
import { EmptyState } from '@/components/empty-state';
import { Stack } from '@/components/stack';
import { Input } from '@/components/input';
import { useSneakerSearch, MIN_QUERY_LENGTH } from '../hooks/use-sneaker-search';
import { SneakerSearchResultCard } from './sneaker-search-result-card';

export function SneakerSearch() {
  const [query, setQuery] = useState('');
  const { results, isLoading, error } = useSneakerSearch(query);

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
              <SneakerSearchResultCard key={sneaker.id} sneaker={sneaker} />
            ))}
          </Stack>
        </AsyncState>
      )}
    </Stack>
  );
}
