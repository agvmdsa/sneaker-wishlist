package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPopularSneakersUseCase implements QueryHandler<GetPopularSneakersQuery, List<SneakerSummaryDto>> {

    private static final String SNEAKERS_ONLY_FILTER = "product_type = sneakers";
    private static final String RANK_SORT = "rank";
    private static final int DEFAULT_LIMIT = 10;

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public GetPopularSneakersUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Override
    @Cacheable("popularSneakers")
    public List<SneakerSummaryDto> execute(GetPopularSneakersQuery query) {
        int limit = query.limit() != null ? query.limit() : DEFAULT_LIMIT;
        String rawBody = sneakerCatalogClient.search(null, SNEAKERS_ONLY_FILTER, RANK_SORT, null, limit);
        return sneakerResponseMapper.toSummaryList(rawBody);
    }
}
