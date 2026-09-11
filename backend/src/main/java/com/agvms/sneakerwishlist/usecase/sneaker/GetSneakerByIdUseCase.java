package com.agvms.sneakerwishlist.usecase.sneaker;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import org.springframework.stereotype.Service;

@Service
public class GetSneakerByIdUseCase {

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public GetSneakerByIdUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    public SneakerSummaryDto execute(String id) {
        String rawBody = sneakerCatalogClient.getById(id);
        return sneakerResponseMapper.toSummary(rawBody);
    }
}
