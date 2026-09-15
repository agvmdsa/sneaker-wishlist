import { useParams } from 'react-router-dom';

export function WishlistItemRoute() {
  const { id } = useParams<{ id: string }>();

  return (
    <main>
      <h1>Wishlist Item {id}</h1>
    </main>
  );
}
