import { PageContainer } from '@/components/page-container';
import { Stack } from '@/components/stack';
import { PopularHero } from '@/features/discovery/components/popular-hero';
import { TrendingRow } from '@/features/discovery/components/trending-row';
import { UpcomingReleasesRow } from '@/features/discovery/components/upcoming-releases-row';

export function HomeRoute() {
  return (
    <PageContainer>
      <h1 className="text-lg font-semibold mb-4">Home</h1>
      <Stack gap="lg">
        <PopularHero />
        <TrendingRow />
        <UpcomingReleasesRow />
      </Stack>
    </PageContainer>
  );
}
