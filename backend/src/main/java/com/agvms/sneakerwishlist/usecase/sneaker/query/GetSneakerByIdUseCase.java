package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.client.SneakerCatalogClient;
import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.QueryHandler;
import com.agvms.sneakerwishlist.usecase.sneaker.SneakerResponseMapper;
import org.springframework.stereotype.Service;

@Service
public class GetSneakerByIdUseCase implements QueryHandler<GetSneakerByIdQuery, SneakerSummaryDto> {

    private final SneakerCatalogClient sneakerCatalogClient;
    private final SneakerResponseMapper sneakerResponseMapper;

    public GetSneakerByIdUseCase(SneakerCatalogClient sneakerCatalogClient, SneakerResponseMapper sneakerResponseMapper) {
        this.sneakerCatalogClient = sneakerCatalogClient;
        this.sneakerResponseMapper = sneakerResponseMapper;
    }

    @Override
    public SneakerSummaryDto execute(GetSneakerByIdQuery query) {
        String rawBody = sneakerCatalogClient.getById(query.id());
        return sneakerResponseMapper.toSummary(rawBody);
    }
}
