import { PageContainer } from '@/components/page-container';
import { WishlistList } from '@/features/wishlist/components/wishlist-list';

export function WishlistListRoute() {
  return (
    <PageContainer>
      <h1 className="text-lg font-semibold mb-4">Sneaker Wishlist</h1>
      <WishlistList />
    </PageContainer>
  );
}
