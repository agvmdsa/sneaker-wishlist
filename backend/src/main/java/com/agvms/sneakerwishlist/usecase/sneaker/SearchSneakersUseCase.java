package com.agvms.sneakerwishlist.usecase.sneaker;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchSneakersUseCase {

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public SearchSneakersUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    public List<SneakerSummaryDto> execute(String query, String filters, String sort, Integer page, Integer limit) {
        String rawBody = sneakerCatalogClient.search(query, filters, sort, page, limit);
        return sneakerResponseMapper.toSummaryList(rawBody);
    }
}
