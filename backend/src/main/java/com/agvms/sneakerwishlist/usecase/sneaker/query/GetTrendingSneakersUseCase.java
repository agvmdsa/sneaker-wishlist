package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.client.KicksDbProduct;
import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class GetTrendingSneakersUseCase implements QueryHandler<GetTrendingSneakersQuery, List<SneakerSummaryDto>> {

    private static final String SNEAKERS_ONLY_FILTER = "product_type = sneakers";
    private static final String RANK_SORT = "rank";
    private static final int CANDIDATE_POOL_SIZE = 100;
    private static final int DEFAULT_LIMIT = 10;

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public GetTrendingSneakersUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Override
    @Cacheable("trendingSneakers")
    public List<SneakerSummaryDto> execute(GetTrendingSneakersQuery query) {
        int limit = query.limit() != null ? query.limit() : DEFAULT_LIMIT;
        String rawBody = sneakerCatalogClient.search(null, SNEAKERS_ONLY_FILTER, RANK_SORT, null, CANDIDATE_POOL_SIZE);
        List<KicksDbProduct> candidatePool = sneakerResponseMapper.toSneakerProducts(rawBody);

        return candidatePool.stream()
                .sorted(Comparator.comparing(KicksDbProduct::weeklyOrders, Comparator.nullsLast(Comparator.<Integer>reverseOrder())))
                .limit(limit)
                .map(sneakerResponseMapper::toSummary)
                .toList();
    }
}
