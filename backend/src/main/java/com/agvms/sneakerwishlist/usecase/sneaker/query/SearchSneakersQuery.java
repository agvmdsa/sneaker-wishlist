package com.agvms.sneakerwishlist.usecase.sneaker.query;

import com.agvms.sneakerwishlist.dto.SneakerSummaryDto;
import com.agvms.sneakerwishlist.usecase.Query;

import java.util.List;

public record SearchSneakersQuery(String query, String filters, String sort, Integer page, Integer limit)
        implements Query<List<SneakerSummaryDto>> {
}
