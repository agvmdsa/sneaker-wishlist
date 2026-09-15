import { useParams } from 'react-router-dom';
import { PageContainer } from '@/components/page-container';

export function WishlistItemRoute() {
  const { id } = useParams<{ id: string }>();

  return (
    <PageContainer>
      <h1>Wishlist Item {id}</h1>
    </PageContainer>
  );
}
