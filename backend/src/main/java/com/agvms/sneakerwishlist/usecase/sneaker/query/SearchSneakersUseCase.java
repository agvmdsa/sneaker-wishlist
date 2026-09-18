package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchSneakersUseCase implements QueryHandler<SearchSneakersQuery, List<SneakerSummaryDto>> {

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public SearchSneakersUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Override
    public List<SneakerSummaryDto> execute(SearchSneakersQuery query) {
        String rawBody = sneakerCatalogClient.search(query.query(), query.filters(), query.sort(), query.page(), query.limit());
        return sneakerResponseMapper.toSummaryList(rawBody);
    }
}
