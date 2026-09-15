import { PageContainer } from '@/components/page-container';
import { SneakerSearch } from '@/features/catalog/components/sneaker-search';

export function SearchRoute() {
  return (
    <PageContainer>
      <h1 className="text-lg font-semibold mb-4">Search Sneakers</h1>
      <SneakerSearch />
    </PageContainer>
  );
}
