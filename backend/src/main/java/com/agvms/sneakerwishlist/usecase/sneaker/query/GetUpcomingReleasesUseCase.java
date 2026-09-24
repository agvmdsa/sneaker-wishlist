package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.dto.UpcomingReleasesDto;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class GetUpcomingReleasesUseCase implements QueryHandler<GetUpcomingReleasesQuery, UpcomingReleasesDto> {

    private static final String RANK_SORT = "rank";
    private static final int BUCKET_LIMIT = 10;
    private static final long SOON_WINDOW_SECONDS = 7L * 24 * 60 * 60;
    private static final long MONTH_WINDOW_SECONDS = 30L * 24 * 60 * 60;

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public GetUpcomingReleasesUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Override
    @Cacheable("upcomingReleases")
    public UpcomingReleasesDto execute(GetUpcomingReleasesQuery query) {
        long now = Instant.now().getEpochSecond();

        List<SneakerSummaryDto> releasingSoon = fetchBucket(now, now + SOON_WINDOW_SECONDS);
        List<SneakerSummaryDto> releasingThisMonth = fetchBucket(now + SOON_WINDOW_SECONDS, now + MONTH_WINDOW_SECONDS);
        List<SneakerSummaryDto> releasingLater = fetchBucket(now + MONTH_WINDOW_SECONDS, null);

        return new UpcomingReleasesDto(releasingSoon, releasingThisMonth, releasingLater);
    }

    private List<SneakerSummaryDto> fetchBucket(long from, Long to) {
        String filters = "product_type = sneakers AND release_date > " + from
                + (to != null ? " AND release_date <= " + to : "");
        String rawBody = sneakerCatalogClient.search(null, filters, RANK_SORT, null, BUCKET_LIMIT);
        return sneakerResponseMapper.toSummaryList(rawBody);
    }
}
