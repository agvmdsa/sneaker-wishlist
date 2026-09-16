import { PageContainer } from '@/components/page-container';
import { WishlistStatsView } from '@/features/stats/components/wishlist-stats-view';

export function StatsRoute() {
  return (
    <PageContainer>
      <h1 className="text-lg font-semibold mb-4">Wishlist Stats</h1>
      <WishlistStatsView />
    </PageContainer>
  );
}
