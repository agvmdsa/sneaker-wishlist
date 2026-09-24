import { PageContainer } from '@/components/page-container';
import { Stack } from '@/components/stack';
import { PopularHero } from '@/features/discovery/components/popular-hero';

export function HomeRoute() {
  return (
    <PageContainer>
      <h1 className="text-lg font-semibold mb-4">Home</h1>
      <Stack gap="lg">
        <PopularHero />
      </Stack>
    </PageContainer>
  );
}
