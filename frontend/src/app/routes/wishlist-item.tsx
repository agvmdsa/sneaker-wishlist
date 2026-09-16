import { useParams } from 'react-router-dom';
import { PageContainer } from '@/components/page-container';
import { WishlistItemDetail } from '@/features/wishlist/components/wishlist-item-detail';

export function WishlistItemRoute() {
  const { id } = useParams<{ id: string }>();
  const itemId = Number(id);

  return (
    <PageContainer>
      <h1 className="text-lg font-semibold mb-4">Wishlist Item</h1>
      <WishlistItemDetail id={itemId} />
    </PageContainer>
  );
}
